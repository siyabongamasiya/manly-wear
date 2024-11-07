package ecommerce.project.manlywear.Domain.Model.retrofit

import androidx.room.PrimaryKey


data class RetrofitProduct(
    val id : Int,
    var title : String,
    var price : String,
    var category : String,
    var description : String,
    var image : String
)
