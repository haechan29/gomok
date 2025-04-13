package haechan.gomok.data.repository

import haechan.gomok.model.Stone
import kotlinx.coroutines.flow.Flow

interface StonesRepository {
    val stones: Flow<List<Stone>>

    suspend fun setStones(stones: List<Stone>)

    suspend fun resetStones()
}