package haechan.gomok.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import haechan.gomok.datastore.mapper.toModel
import java.io.IOException
import javax.inject.Inject
import haechan.gomok.model.Preview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import haechan.gomok.datastore.Preview as PreviewData

class PreviewDataSource @Inject constructor(
    private val previewDataStore: DataStore<PreviewData>,
) {
    val preview: Flow<Preview> = previewDataStore.data.map(PreviewData::toModel)

    suspend fun setPreview(preview: Preview) {
        try {
            previewDataStore.updateData {
                it.toBuilder()
                    .setRow(preview.row)
                    .setColumn(preview.column)
                    .build()
            }
        } catch (ioException: IOException) {
            Log.e("PreviewDataSource", "Failed to set preview", ioException)
        }
    }

    suspend fun resetPreview() {
        try {
            previewDataStore.updateData {
                it.toBuilder()
                    .clearRow()
                    .clearColumn()
                    .build()
            }
        } catch (ioException: IOException) {
            Log.e("PreviewDataSource", "Failed to reset preview", ioException)
        }
    }
}