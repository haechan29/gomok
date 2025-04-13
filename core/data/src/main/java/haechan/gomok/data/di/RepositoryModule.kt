package haechan.gomok.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import haechan.gomok.data.repository.OwnerRepository
import haechan.gomok.data.repository.OwnerRepositoryImpl
import haechan.gomok.data.repository.PreviewRepository
import haechan.gomok.data.repository.PreviewRepositoryImpl
import haechan.gomok.data.repository.RecordRepository
import haechan.gomok.data.repository.RecordRepositoryImpl
import haechan.gomok.data.repository.StonesRepository
import haechan.gomok.data.repository.StonesRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    internal abstract fun bindsPreviewRepository(
        previewRepository: PreviewRepositoryImpl,
    ): PreviewRepository

    @Binds
    internal abstract fun bindsStoneRepository(
        stoneRepository: StonesRepositoryImpl,
    ): StonesRepository

    @Binds
    internal abstract fun bindsOwnerRepository(
        ownerRepository: OwnerRepositoryImpl,
    ): OwnerRepository

    @Binds
    internal abstract fun bindsRecordRepository(
        recordRepository: RecordRepositoryImpl,
    ): RecordRepository
}