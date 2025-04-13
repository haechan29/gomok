package haechan.gomok.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import haechan.gomok.datastore.mapper.toModel
import haechan.gomok.model.Stone
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import haechan.gomok.datastore.StoneList as StoneListData
import haechan.gomok.datastore.Stone as StoneData

class StonesDataSource @Inject constructor(
    private val stonesDataStore: DataStore<StoneListData>,
) {
    val stones: Flow<List<Stone>> = stonesDataStore.data.map(StoneListData::toModel)

    suspend fun setStones(stones: List<Stone>) {
        try {
            val stoneDataList = stones.map {
                StoneData.newBuilder()
                    .setOwner(Owner.valueOf(it.owner.name))
                    .setRow(it.row)
                    .setColumn(it.column)
                    .build()
            }
            stonesDataStore.updateData {
                it.toBuilder()
                    .addAllStone(stoneDataList)
                    .build()
            }
        } catch (ioException: IOException) {
            Log.e("StoneDataSource", "Failed to set stones", ioException)
        }
    }

    suspend fun resetStones() {
        try {
            stonesDataStore.updateData {
                it.toBuilder()
                    .clearStone()
                    .build()
            }
        } catch (ioException: IOException) {
            Log.e("StoneDataSource", "Failed to reset stones", ioException)
        }
    }
}