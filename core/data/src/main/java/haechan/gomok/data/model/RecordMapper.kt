package haechan.gomok.data.model

import haechan.gomok.database.model.RecordEntity
import haechan.gomok.model.Record

fun Record.toEntity(): RecordEntity = RecordEntity(
    owner = owner,
    recordedAt = recordedAt
)