package haechan.gomok.game.presentation

import haechan.gomok.model.Stone
import javax.inject.Inject

class GameManager @Inject constructor() {
    fun isGameFinished(board: Board, stone: Stone): Boolean {
        return ((0 until 8) * (-4 .. 0))
            .any { (d, i) ->
                try {
                    (0..4)
                        .map {
                            board.getCell(
                                stone.row + dr[d] * (i + it),
                                stone.column + dc[d] * (i + it)
                            )
                        }
                        .all { it is Stone && it.owner == stone.owner }
                } catch (_: Exception) {
                    false
                }
            }
    }

    private operator fun IntRange.times(other: IntRange): List<Pair<Int, Int>> {
        return this.flatMap { a -> other.map { b -> a to b } }
    }
}

private val dr = arrayOf(-1, -1, 0, 1, 1, 1, 0, -1)
private val dc = arrayOf(0, 1, 1, 1, 0, -1, -1, -1)