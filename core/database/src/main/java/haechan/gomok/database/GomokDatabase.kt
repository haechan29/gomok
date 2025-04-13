package haechan.gomok.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import haechan.gomok.database.dao.RecordDao
import haechan.gomok.database.model.RecordEntity

@Database(
    entities = [
        RecordEntity::class,
    ],
    version = 1,
)
@TypeConverters(
    OwnerConverter::class,
)
internal abstract class GomokDatabase: RoomDatabase() {
    abstract fun recordDao(): RecordDao
}