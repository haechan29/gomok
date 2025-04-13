package haechan.gomok.data.repository

import haechan.gomok.model.Preview
import kotlinx.coroutines.flow.Flow

interface PreviewRepository {
    val preview: Flow<Preview>

    suspend fun setPreview(preview: Preview)

    suspend fun resetPreview()
}