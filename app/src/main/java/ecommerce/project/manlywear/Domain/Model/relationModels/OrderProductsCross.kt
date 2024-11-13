package ecommerce.project.manlywear.Domain.Model.relationModels

import androidx.room.Entity


@Entity(primaryKeys = ["orderId","productId"])
data class OrderProductCross(
    val orderId : Int,
    val productId : Int,
)
