package ecommerce.project.manlywear.Domain.Model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ShoppableProducts")
data class ShoppableProduct(
    @PrimaryKey(autoGenerate = false)
    val productId : Int,
    @ColumnInfo(name = "title")
    var title : String,
    @ColumnInfo(name = "price")
    var price : String,
    @ColumnInfo(name = "category")
    var category : String,
    @ColumnInfo(name = "description")
    var description : String,
    @ColumnInfo(name = "image")
    var image : String
)
