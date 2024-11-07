package ecommerce.project.manlywear.Domain.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Basket")
data class RoomProduct(
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    @ColumnInfo(name = "title")
    var title : String,
    @ColumnInfo(name = "price")
    var price : String,
    @ColumnInfo(name = "category")
    var category : String,
    @ColumnInfo(name = "description")
    var description : String,
    @ColumnInfo(name = "image")
    var image : String,
    @ColumnInfo(name = "number of ordered items")
    var no_of_ordered_items : Int,
    @ColumnInfo(name = "order id")
    var orderId : Int
)
