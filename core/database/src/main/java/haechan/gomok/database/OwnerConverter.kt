package haechan.gomok.database

import androidx.room.TypeConverter
import haechan.gomok.model.Owner

internal class OwnerConverter {
    @TypeConverter
    fun fromOwner(owner: Owner): String = owner.name

    @TypeConverter
    fun toOwner(owner: String): Owner = Owner.valueOf(owner)
}