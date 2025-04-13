package haechan.gomok.data.repository

import haechan.gomok.datastore.OwnerDataSource
import haechan.gomok.model.Owner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OwnerRepositoryImpl @Inject constructor(
    private val ownerDataSource: OwnerDataSource,
): OwnerRepository {
    override val owner: Flow<Owner> = ownerDataSource.owner

    override suspend fun setOwner(owner: Owner) =
        ownerDataSource.setOwner(owner)

    override suspend fun resetOwner() =
        ownerDataSource.resetOwner()
}