package haechan.gomok.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import haechan.gomok.database.model.RecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Query("SELECT * FROM records ORDER BY recordedAt DESC")
    fun getAllRecords(): Flow<List<RecordEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreRecord(recordEntity: RecordEntity): Long
}