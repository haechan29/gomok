package haechan.gomok.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Cell: Parcelable {
    abstract val row: Int
    abstract val column: Int
}

@Parcelize
data class Preview(
    override val row: Int,
    override val column: Int,
): Cell()

@Parcelize
data class Stone(
    val owner: Owner,
    override val row: Int,
    override val column: Int,
): Cell()

@Parcelize
enum class Owner: Parcelable {
    BLACK,
    WHITE,
    NO_OWNER;

    fun toggle() =
        when (this) {
            BLACK -> WHITE
            WHITE -> BLACK
            NO_OWNER -> throw Exception("NO_OWNER은 toggle()을 호출할 수 없습니다.")
        }
}
