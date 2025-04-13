package haechan.gomok.data.repository

import haechan.gomok.datastore.PreviewDataSource
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import haechan.gomok.model.Preview

class PreviewRepositoryImpl @Inject constructor(
    private val previewDataSource: PreviewDataSource,
): PreviewRepository {
    override val preview = previewDataSource.preview

    override suspend fun setPreview(preview: Preview) =
        previewDataSource.setPreview(preview)

    override suspend fun resetPreview() =
        previewDataSource.resetPreview()
}