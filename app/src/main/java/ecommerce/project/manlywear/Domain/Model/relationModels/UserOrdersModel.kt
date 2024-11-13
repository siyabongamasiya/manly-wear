package ecommerce.project.manlywear.Domain.Model.relationModels

import androidx.room.Embedded
import androidx.room.Relation
import ecommerce.project.manlywear.Domain.Model.room.Order
import ecommerce.project.manlywear.Domain.Model.room.User

data class UserOrdersModel(
    @Embedded
    val user: User,
    @Relation(
        parentColumn = "username",
        entityColumn = "userId"
    )
    val order: List<Order>

)
