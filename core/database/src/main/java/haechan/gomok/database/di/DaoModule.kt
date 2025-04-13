package haechan.gomok.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import haechan.gomok.database.GomokDatabase
import haechan.gomok.database.dao.RecordDao

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {
    @Provides
    fun providesRecordDao(
        database: GomokDatabase,
    ): RecordDao = database.recordDao()
}