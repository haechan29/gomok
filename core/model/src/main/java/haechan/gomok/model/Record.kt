package haechan.gomok.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Parcelize
data class Record(
    val id: Int = 0,
    val owner: Owner,
    val recordedAt: Long,
): Parcelable {
    val formattedRecordedAt: String
        get() = SimpleDateFormat("yyyy년 M월 d일 HH:mm", Locale.getDefault())
            .format(Date(recordedAt))

    val winnerText: String
        get() = when (owner) {
            Owner.BLACK -> "흑돌이 승리했습니다."
            Owner.WHITE -> "백돌이 승리했습니다."
            Owner.NO_OWNER -> throw Exception("Unreachable code.")
        }
}