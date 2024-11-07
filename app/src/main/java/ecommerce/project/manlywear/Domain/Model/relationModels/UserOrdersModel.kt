package ecommerce.project.manlywear.Domain.Model

import androidx.room.Embedded
import androidx.room.Relation

data class UserOrdersModel(
    @Embedded
    val user: User,
    @Relation(
        parentColumn = "username",
        
    )
    val order: List<Order>,

)
