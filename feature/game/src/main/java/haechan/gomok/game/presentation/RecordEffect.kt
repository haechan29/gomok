package haechan.gomok.game.presentation

import haechan.gomok.model.Record

sealed class RecordEffect {
    data class NavigateToRecordActivity(val records: List<Record>): RecordEffect()
}