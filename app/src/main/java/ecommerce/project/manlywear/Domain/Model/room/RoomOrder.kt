package ecommerce.project.manlywear.Domain.Model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.util.Date

data class Order(
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    @ColumnInfo(name = "userId")
    var userId : String,
    @ColumnInfo(name = "Status")
    var status : String,
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
