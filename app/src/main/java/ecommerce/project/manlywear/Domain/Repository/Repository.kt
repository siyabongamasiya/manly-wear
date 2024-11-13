package ecommerce.project.manlywear.Domain.Repository

import ecommerce.project.manlywear.Domain.Model.relationModels.OrderProductCross
import ecommerce.project.manlywear.Domain.Model.relationModels.OrderWithProducts
import ecommerce.project.manlywear.Domain.Model.retrofit.RetrofitProduct
import ecommerce.project.manlywear.Domain.Model.room.BasketProduct
import ecommerce.project.manlywear.Domain.Model.room.Order
import ecommerce.project.manlywear.Domain.Model.room.OrderProduct
import ecommerce.project.manlywear.Domain.Model.room.ShoppableProduct
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repository {

    suspend fun fetchProductsFromRemote() : List<RetrofitProduct>?

    suspend fun saveShoppableProducts(list: List<ShoppableProduct>)

    suspend fun fetchShoppableProductsLocally() : List<ShoppableProduct>

    suspend fun getShoppableProductByID(productID : Int) : ShoppableProduct?

    suspend fun saveBasketProduct(basketProduct: BasketProduct)

    suspend fun getBasketProducts() : List<BasketProduct>

    suspend fun deleteBasketProducts(basketProduct: BasketProduct)

    suspend fun saveOrderProductsCross(orderProductCross: List<OrderProductCross>)

    suspend fun saveOrder(order: Order)

    suspend fun saveOrderProducts(orderProduct: List<OrderProduct>)

    suspend fun getOrdersWithProducts() : List<OrderWithProducts>

    suspend fun getOrderById(orderId : Int) : Order

    suspend fun deleteOrderById(orderId: Int)

    suspend fun deleteAllBasketProducts()
}