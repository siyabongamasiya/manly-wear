package ecommerce.project.manlywear.Domain.Model.relationModels

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ecommerce.project.manlywear.Domain.Model.room.Order
import ecommerce.project.manlywear.Domain.Model.room.BasketProduct
import ecommerce.project.manlywear.Domain.Model.room.OrderProduct

data class OrderWithProducts(
    @Embedded
    val order :Order,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "productId",
        associateBy = Junction(OrderProductCross::class)
    )
    val orderProducts: List<OrderProduct>
)
