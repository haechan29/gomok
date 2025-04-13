package haechan.gomok.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class PreviewSerializer @Inject constructor() : Serializer<Preview> {
    override val defaultValue: Preview = Preview.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Preview {
        return try {
            Preview.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: Preview, output: OutputStream) {
        t.writeTo(output)
    }
}