package haechan.gomok.game.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import haechan.gomok.data.repository.OwnerRepository
import haechan.gomok.data.repository.PreviewRepository
import haechan.gomok.data.repository.RecordRepository
import haechan.gomok.data.repository.StonesRepository
import haechan.gomok.model.Owner
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import haechan.gomok.model.Preview
import haechan.gomok.model.Record
import haechan.gomok.model.Stone
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class GameViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val previewRepository: PreviewRepository,
    private val stonesRepository: StonesRepository,
    private val ownerRepository: OwnerRepository,
    private val recordRepository: RecordRepository,
    private val gameManager: GameManager,
): ViewModel() {
    private val _isGameFinishedFlow = MutableStateFlow(false)
    val isGameFinishedFlow: StateFlow<Boolean> = _isGameFinishedFlow.asStateFlow()

    private val previewFlow: StateFlow<Preview?> =
        savedStateHandle.getStateFlow(PREVIEW_KEY, null)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                null,
            )

    private val stonesFlow: StateFlow<ArrayList<Stone>> =
        savedStateHandle.getStateFlow(STONES_KEY, arrayListOf())

    private val stoneFlow: StateFlow<Stone?> = stonesFlow
        .map { it.lastOrNull() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            null,
        )

    val boardFlow: StateFlow<Board> =
        combine(
            previewFlow,
            stonesFlow.filterNotNull(),
        ) { preview, stones ->
            toBoard(preview, stones)
        }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                Board(),
            )

    val ownerFlow: StateFlow<Owner> =
        savedStateHandle.getStateFlow(OWNER_KEY, Owner.BLACK)

    private val timeCountFlow: Flow<Int> =
        ownerFlow.flatMapLatest {
            flow {
                for (t in 300 downTo 0) {
                    delay(100)
                    emit(t)
                }
            }
        }

    private val isTimeCountDoneFlow: StateFlow<Boolean> = timeCountFlow
        .map { it == 0 }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            false,
        )

    val timeCountProgressFlow: StateFlow<Int> =
        combine(
            timeCountFlow,
            isGameFinishedFlow,
        ) { timeCount, isGameFinished ->
            Pair(timeCount, isGameFinished)
        }
            .map { (timeCount, isGameFinished) ->
                if (isGameFinished) 0
                else timeCount / 3
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                100,
            )

    val gameStateFlow: StateFlow<GameState> =
        combine(
            ownerFlow,
            stoneFlow,
            isTimeCountDoneFlow,
        ) { owner, stone, isTimeCountDone ->
            if (isTimeCountDone) {
                if (owner == Owner.BLACK) return@combine GameState.BLACK_WIN
                if (owner == Owner.WHITE) return@combine GameState.WHITE_WIN
            }
            return@combine stone?.let { getGameState(it) } ?: GameState.IN_PROGRESS
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            GameState.IN_PROGRESS,
        )

    fun setPreview(row: Int, column: Int) {
        if (isGameFinishedFlow.value) return
        val cell = boardFlow.value.getCell(row, column)
        if (cell is Stone && cell.owner != Owner.NO_OWNER) return
        savedStateHandle[PREVIEW_KEY] =
            if (previewFlow.value?.row != row || previewFlow.value?.column != column) {
                Preview(row, column)
            } else {
                null
            }
    }

    fun putStone() {
        if (isGameFinishedFlow.value) return
        val stone = previewFlow.value?.let {
            Stone(ownerFlow.value, it.row, it.column)
        } ?: return
        savedStateHandle[STONES_KEY] = ArrayList(stonesFlow.value + stone)
        savedStateHandle[PREVIEW_KEY] = null
        savedStateHandle[OWNER_KEY] = ownerFlow.value.toggle()
    }

    fun saveData() {
        viewModelScope.launch {
            previewFlow.value?.let { previewRepository.setPreview(it) }
            stonesRepository.setStones(stonesFlow.value)
            ownerRepository.setOwner(ownerFlow.value)
        }
    }

    fun clearData() {
        viewModelScope.launch {
            previewRepository.resetPreview()
            stonesRepository.resetStones()
            ownerRepository.resetOwner()
        }
    }

    fun setData(preview: Preview, owner: Owner, stones: List<Stone>) {
        viewModelScope.launch {
            savedStateHandle[PREVIEW_KEY] = preview
            savedStateHandle[STONES_KEY] = stones
            savedStateHandle[OWNER_KEY] = owner
        }
    }

    private fun toBoard(preview: Preview?, stones: ArrayList<Stone>): Board {
        val board = Board()
        stones.forEach {
            board.setCell(Stone(it.owner, it.row, it.column))
        }
        preview?.let { board.setCell(it) }
        return board
    }

    private fun getGameState(stone: Stone): GameState {
        if (stone.owner == Owner.NO_OWNER) return GameState.IN_PROGRESS
        val board = boardFlow.value
        val isGameFinished = gameManager.isGameFinished(board, stone)

        if (!isGameFinished) return GameState.IN_PROGRESS
        return when (stone.owner) {
            Owner.BLACK -> GameState.BLACK_WIN
            Owner.WHITE -> GameState.WHITE_WIN
            else -> throw Exception("Unreachable code.")
        }
    }

    init {
        gameStateFlow
            .map { it != GameState.IN_PROGRESS }
            .onEach { _isGameFinishedFlow.tryEmit(it) }
            .launchIn(viewModelScope)

        gameStateFlow
            .filter { it != GameState.IN_PROGRESS }
            .onEach { gameState ->
                val owner = when (gameState) {
                    GameState.BLACK_WIN -> Owner.BLACK
                    GameState.WHITE_WIN -> Owner.WHITE
                    GameState.IN_PROGRESS -> throw Exception("Unreachable code.")
                }
                recordRepository.insertOrIgnoreRecord(
                    Record(
                        owner = owner,
                        recordedAt = System.currentTimeMillis()
                    )
                )
            }
            .launchIn(viewModelScope)
    }
}

private const val PREVIEW_KEY = "PREVIEW_KEY"
private const val STONES_KEY = "STONES_KEY"
private const val OWNER_KEY = "OWNER_KEY"