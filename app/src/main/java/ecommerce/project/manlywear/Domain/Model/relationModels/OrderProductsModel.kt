package ecommerce.project.manlywear.Domain.Model.relationModels

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ecommerce.project.manlywear.Domain.Model.room.RoomOrder
import ecommerce.project.manlywear.Domain.Model.room.RoomProduct

data class OrderProductsModel(
    @Embedded
    val roomOrder :RoomOrder,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "productId",
        associateBy = Junction(OrderProductsCross::class)
    )
    val roomProduct: List<RoomProduct>
)
