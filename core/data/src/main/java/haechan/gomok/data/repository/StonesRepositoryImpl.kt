package haechan.gomok.data.repository

import haechan.gomok.datastore.StonesDataSource
import haechan.gomok.model.Stone
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StonesRepositoryImpl @Inject constructor(
    private val stonesDataSource: StonesDataSource,
): StonesRepository {
    override val stones: Flow<List<Stone>> = stonesDataSource.stones

    override suspend fun setStones(stones: List<Stone>)
        = stonesDataSource.setStones(stones)

    override suspend fun resetStones()
        = stonesDataSource.resetStones()
}