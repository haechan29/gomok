package haechan.gomok.data.repository

import haechan.gomok.model.Record
import kotlinx.coroutines.flow.Flow

interface RecordRepository {
    fun getAllRecords(): Flow<List<Record>>
    suspend fun insertOrIgnoreRecord(record: Record)
}