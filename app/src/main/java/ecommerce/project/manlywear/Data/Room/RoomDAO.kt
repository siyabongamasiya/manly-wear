package ecommerce.project.manlywear.Data.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import ecommerce.project.manlywear.Domain.Model.relationModels.OrderProductCross
import ecommerce.project.manlywear.Domain.Model.relationModels.OrderWithProducts
import ecommerce.project.manlywear.Domain.Model.room.BasketProduct
import ecommerce.project.manlywear.Domain.Model.room.Order
import ecommerce.project.manlywear.Domain.Model.room.OrderProduct
import ecommerce.project.manlywear.Domain.Model.room.ShoppableProduct

@Dao
interface  BasketRoomDAO {

    // Insert a single product
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: BasketProduct)

    // Insert multiple products
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<BasketProduct>)

    // Retrieve all products
    @Query("SELECT * FROM BasketProducts")
    suspend fun getAllProducts(): List<BasketProduct>

    // Retrieve a product by its ID
    @Query("SELECT * FROM BASKETPRODUCTS WHERE productId = :productId")
    suspend fun getProductById(productId: Int): BasketProduct?

    // Update a product (optional: Room updates entities automatically if using `REPLACE` in insert)
    @Update
    suspend fun updateProduct(product: BasketProduct)

    // Delete a single product
    @Delete
    suspend fun deleteProduct(product: BasketProduct)

    // Delete all products
    @Query("DELETE FROM BasketProducts")
    suspend fun deleteAllProducts()
}

@Dao
interface  ShoppableRoomDAO {

    // Insert a single product
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ShoppableProduct)

    // Insert multiple products
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ShoppableProduct>)

    // Retrieve all products
    @Query("SELECT * FROM ShoppableProducts")
    suspend fun getAllProducts(): List<ShoppableProduct>

    // Retrieve a product by its ID
    @Query("SELECT * FROM ShoppableProducts WHERE productId = :productId")
    suspend fun getProductById(productId: Int): ShoppableProduct?

    // Update a product (optional: Room updates entities automatically if using `REPLACE` in insert)
    @Update
    suspend fun updateProduct(product: ShoppableProduct)

    // Delete a single product
    @Delete
    suspend fun deleteProduct(product: ShoppableProduct)

    // Delete all products
    @Query("DELETE FROM ShoppableProducts")
    suspend fun deleteAllProducts()
}

@Dao
interface OrderRoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderProductCrossRefs(crossRefs: List<OrderProductCross>)

    @Transaction
    @Query("SELECT * FROM `Orders` WHERE orderId = :orderId")
    suspend fun getOrderWithProductsById(orderId: Int): OrderWithProducts

    @Transaction
    @Query("SELECT * FROM `Orders`")
    suspend fun getOrdersWithProducts(): List<OrderWithProducts>

    @Query("DELETE FROM `Orders` WHERE orderId = :orderId")
    suspend fun deleteOrder(orderId: Int)

    @Query("SELECT * FROM `Orders` WHERE orderId= :orderId")
    suspend fun getOrderById(orderId: Int) : Order
    @Query("SELECT * FROM `Orders`")
    suspend fun getAllOrders(): List<Order>
}


@Dao
interface OrderProductRoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderProduct(orderProduct: OrderProduct)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderProducts(orderProducts: List<OrderProduct>)

    @Query("SELECT * FROM OrderProducts WHERE productId = :productId")
    suspend fun getOrderProduct(productId: Int): OrderProduct?

    @Query("SELECT * FROM OrderProducts")
    suspend fun getAllOrderProducts(): List<OrderProduct>
}