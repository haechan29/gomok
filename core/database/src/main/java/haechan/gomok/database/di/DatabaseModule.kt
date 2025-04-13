package haechan.gomok.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import haechan.gomok.database.GomokDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesGomokDatabase(
        @ApplicationContext context: Context,
    ): GomokDatabase = Room.databaseBuilder(
        context,
        GomokDatabase::class.java,
        "gomok-database",
    ).build()
}
