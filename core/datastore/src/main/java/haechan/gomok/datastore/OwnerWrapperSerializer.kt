package haechan.gomok.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class OwnerWrapperSerializer @Inject constructor() : Serializer<OwnerWrapper> {
    override val defaultValue: OwnerWrapper = OwnerWrapper.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): OwnerWrapper {
        return try {
            OwnerWrapper.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: OwnerWrapper, output: OutputStream) {
        t.writeTo(output)
    }
}