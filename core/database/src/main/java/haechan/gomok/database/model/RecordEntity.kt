package haechan.gomok.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import haechan.gomok.model.Owner
import haechan.gomok.model.Record

@Entity(tableName = "records")
data class RecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val owner: Owner,
    @ColumnInfo
    val recordedAt: Long,
) {
    fun toModel() = Record(id, owner, recordedAt)
}