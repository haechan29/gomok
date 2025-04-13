package haechan.gomok.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class StonesSerializer @Inject constructor() : Serializer<StoneList> {
    override val defaultValue: StoneList = StoneList.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): StoneList {
        return try {
            StoneList.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: StoneList, output: OutputStream) {
        t.writeTo(output)
    }
}