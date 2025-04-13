package haechan.gomok.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import haechan.gomok.data.repository.OwnerRepository
import haechan.gomok.data.repository.PreviewRepository
import haechan.gomok.data.repository.RecordRepository
import haechan.gomok.data.repository.StonesRepository
import haechan.gomok.model.Owner
import haechan.gomok.model.Preview
import haechan.gomok.model.Stone
import haechan.gomok.model.Record
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val previewRepository: PreviewRepository,
    private val stonesRepository: StonesRepository,
    private val ownerRepository: OwnerRepository,
): ViewModel() {
    private val effectChannel = Channel<MainEffect>()
    val effectChannelFlow = effectChannel.receiveAsFlow()

    private val preview: StateFlow<Preview?> =
        previewRepository.preview
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                null,
            )

    private val owner: StateFlow<Owner?> =
        ownerRepository.owner
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                null,
            )

    private val stones: StateFlow<List<Stone>> =
        stonesRepository.stones
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                arrayListOf(),
            )

    val hasLocalData: StateFlow<Boolean> =
        combine(preview, owner, stones) { preview, owner, stones ->
            preview != null &&
                owner != null &&
                stones.isNotEmpty()
        }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                false,
            )

    fun navigateToGameActivity() {
        effectChannel.trySend(MainEffect.NavigateToGameActivity)
    }

    fun resumeGame() {
        val preview = preview.value ?: return
        val owner = owner.value ?: return
        val stones = stones.value
        effectChannel.trySend(MainEffect.ResumeGameActivity(preview, owner, stones))
    }
}