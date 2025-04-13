package haechan.gomok.game.presentation

import haechan.gomok.model.Cell
import haechan.gomok.model.Owner
import haechan.gomok.model.Stone

class Board {
    private val boardArray: Array<Array<Cell>> =
        Array(19) { row -> Array(19) { column -> Stone(Owner.NO_OWNER, row, column) } }

    fun getCell(row: Int, column: Int): Cell {
        return boardArray[row][column]
    }

    fun setCell(cell: Cell) {
        boardArray[cell.row][cell.column] = cell
    }
}