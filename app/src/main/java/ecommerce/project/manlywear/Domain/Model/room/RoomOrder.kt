package ecommerce.project.manlywear.Domain.Model.room

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class RoomOrder(
    @PrimaryKey(autoGenerate = false)
    val orderId : Int,
    @ColumnInfo(name = "userId")
    var userId : String,
    @ColumnInfo(name = "Status")
    var status : Int,
    @ColumnInfo(name = "Total Cost")
    var totalCost : Int,
    @ColumnInfo(name = "Delivery Address")
    var deliveryAddress : String,
    @ColumnInfo(name = "Phone Number")
    var phoneNumber : String ,
    @ColumnInfo(name = "Card Holder")
    var cardHolder : String,
    @ColumnInfo(name = "Card Number")
    var cardNumber : String,
    @ColumnInfo(name = "Date")
    var date : String,
    @ColumnInfo(name = "CCV")
    var ccv : String

)
