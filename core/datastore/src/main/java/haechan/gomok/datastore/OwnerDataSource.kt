package haechan.gomok.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import haechan.gomok.datastore.mapper.toModel
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import haechan.gomok.model.Owner
import haechan.gomok.datastore.Owner as OwnerData

class OwnerDataSource @Inject constructor(
    private val ownerWrapperDataStore: DataStore<OwnerWrapper>,
) {
    val owner: Flow<Owner> = ownerWrapperDataStore.data
        .map { it.owner }
        .map(OwnerData::toModel)

    suspend fun setOwner(owner: Owner) {
        try {
            ownerWrapperDataStore.updateData {
                it.toBuilder()
                    .setOwner(OwnerData.valueOf(owner.name))
                    .build()
            }
        } catch (ioException: IOException) {
            Log.e("OwnerDataSource", "Failed to set owner", ioException)
        }
    }

    suspend fun resetOwner() {
        try {
            ownerWrapperDataStore.updateData {
                it.toBuilder()
                    .clearOwner()
                    .build()
            }
        } catch (ioException: IOException) {
            Log.e("OwnerDataSource", "Failed to reset owner", ioException)
        }
    }
}