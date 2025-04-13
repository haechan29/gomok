/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package haechan.gomok.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import haechan.gomok.common.network.Dispatcher
import haechan.gomok.common.network.GomokDispatchers
import haechan.gomok.common.network.di.ApplicationScope
import haechan.gomok.datastore.OwnerWrapperSerializer
import haechan.gomok.datastore.OwnerWrapper
import haechan.gomok.datastore.Preview
import haechan.gomok.datastore.PreviewSerializer
import haechan.gomok.datastore.Stone
import haechan.gomok.datastore.StoneList
import haechan.gomok.datastore.StonesSerializer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    internal fun providesPreviewDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(GomokDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
        previewSerializer: PreviewSerializer,
    ): DataStore<Preview> =
        DataStoreFactory.create(
            serializer = previewSerializer,
            scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
        ) {
            context.dataStoreFile("preview.pb")
        }

    @Provides
    @Singleton
    internal fun providesStonesDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(GomokDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
        stonesSerializer: StonesSerializer,
    ): DataStore<StoneList> =
        DataStoreFactory.create(
            serializer = stonesSerializer,
            scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
        ) {
            context.dataStoreFile("stones.pb")
        }

    @Provides
    @Singleton
    internal fun providesOwnerWrapperDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(GomokDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
        ownerWrapperSerializer: OwnerWrapperSerializer,
    ): DataStore<OwnerWrapper> =
        DataStoreFactory.create(
            serializer = ownerWrapperSerializer,
            scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
        ) {
            context.dataStoreFile("owner.pb")
        }
}
