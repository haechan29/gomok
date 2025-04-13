package haechan.gomok.game.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import haechan.gomok.data.repository.OwnerRepository
import haechan.gomok.data.repository.PreviewRepository
import haechan.gomok.data.repository.RecordRepository
import haechan.gomok.data.repository.StonesRepository
import haechan.gomok.model.Record
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val recordRepository: RecordRepository,
): ViewModel() {
    private val effectChannel = Channel<RecordEffect>()
    val effectChannelFlow = effectChannel.receiveAsFlow()

    private val records: StateFlow<List<Record>> =
        recordRepository.getAllRecords()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                emptyList(),
            )

    val hasRecords: StateFlow<Boolean> =
        records
            .map(List<Record>::isNotEmpty)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                false,
            )

    fun navigateToRecordActivity() {
        effectChannel.trySend(RecordEffect.NavigateToRecordActivity(records.value))
    }
}