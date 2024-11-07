package ecommerce.project.manlywear.Domain.Model.relationModels

import androidx.room.Embedded
import androidx.room.Relation
import ecommerce.project.manlywear.Domain.Model.room.RoomOrder
import ecommerce.project.manlywear.Domain.Model.room.RoomUser

data class UserOrdersModel(
    @Embedded
    val user: RoomUser,
    @Relation(
        parentColumn = "username",
        entityColumn = "userId"
    )
    val order: List<RoomOrder>

)
