package haechan.gomok.datastore.mapper

import haechan.gomok.model.Preview
import haechan.gomok.datastore.Preview as PreviewData

fun PreviewData.toModel(): Preview {
    return Preview(
        row = this.row,
        column = this.column,
    )
}