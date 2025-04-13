package haechan.gomok.datastore.mapper

import haechan.gomok.model.Owner
import haechan.gomok.datastore.Owner as OwnerData

fun OwnerData.toModel() = Owner.valueOf(this.name)