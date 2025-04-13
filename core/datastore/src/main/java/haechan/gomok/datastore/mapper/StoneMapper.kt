package haechan.gomok.datastore.mapper

import haechan.gomok.model.Stone
import haechan.gomok.datastore.StoneList as StoneListData

fun StoneListData.toModel(): List<Stone> {
    return this.stoneList.map {
        Stone(
            owner = it.owner.toModel(),
            row = it.row,
            column = it.column,
        )
    }
}