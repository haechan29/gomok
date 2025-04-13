package haechan.gomok.data.repository

import haechan.gomok.model.Owner
import kotlinx.coroutines.flow.Flow

interface OwnerRepository {
    val owner: Flow<Owner>

    suspend fun setOwner(owner: Owner)

    suspend fun resetOwner()
}