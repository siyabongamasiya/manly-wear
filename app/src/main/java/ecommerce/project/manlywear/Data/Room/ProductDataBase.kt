package ecommerce.project.manlywear.Data.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import ecommerce.project.manlywear.Domain.Model.relationModels.OrderProductCross
import ecommerce.project.manlywear.Domain.Model.room.BasketProduct
import ecommerce.project.manlywear.Domain.Model.room.Order
import ecommerce.project.manlywear.Domain.Model.room.OrderProduct
import ecommerce.project.manlywear.Domain.Model.room.ShoppableProduct
import ecommerce.project.manlywear.Domain.Model.room.User

@Database(entities = [ShoppableProduct::class,
    BasketProduct::class,
    Order::class,
    OrderProduct::class,
    OrderProductCross::class,
    User::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun shoppableDao(): ShoppableRoomDAO
    abstract fun basketDao(): BasketRoomDAO
    abstract fun orderDao(): OrderRoomDao
    abstract fun orderProductDao(): OrderProductRoomDao

}