package haechan.gomok.data.repository

import haechan.gomok.data.model.toEntity
import haechan.gomok.database.dao.RecordDao
import haechan.gomok.database.model.RecordEntity
import haechan.gomok.model.Record
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(
    private val recordDao: RecordDao,
): RecordRepository {
    override fun getAllRecords(): Flow<List<Record>> {
        return recordDao.getAllRecords().map { it.map(RecordEntity::toModel) }
    }

    override suspend fun insertOrIgnoreRecord(record: Record) {
        recordDao.insertOrIgnoreRecord(record.toEntity())
    }
}