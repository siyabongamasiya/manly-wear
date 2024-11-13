package ecommerce.project.manlywear.Data.RepositoryImpl

import ecommerce.project.manlywear.Data.Retrofit.ProductApiService
import ecommerce.project.manlywear.Data.Room.BasketRoomDAO
import ecommerce.project.manlywear.Data.Room.OrderProductRoomDao
import ecommerce.project.manlywear.Data.Room.OrderRoomDao
import ecommerce.project.manlywear.Data.Room.ShoppableRoomDAO
import ecommerce.project.manlywear.Domain.Model.relationModels.OrderProductCross
import ecommerce.project.manlywear.Domain.Model.relationModels.OrderWithProducts
import ecommerce.project.manlywear.Domain.Repository.Repository
import ecommerce.project.manlywear.Domain.Model.retrofit.RetrofitProduct
import ecommerce.project.manlywear.Domain.Model.room.BasketProduct
import ecommerce.project.manlywear.Domain.Model.room.Order
import ecommerce.project.manlywear.Domain.Model.room.OrderProduct
import ecommerce.project.manlywear.Domain.Model.room.ShoppableProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    val productApiService: ProductApiService,
    val shoppableRoomDAO: ShoppableRoomDAO,
    val basketRoomDAO: BasketRoomDAO,
    val orderRoomDao: OrderRoomDao,
    val orderProductRoomDao: OrderProductRoomDao) : Repository {
    override suspend fun fetchProductsFromRemote(): List<RetrofitProduct>? {
        return productApiService.fetchAllProducts().body()
    }

    override suspend fun saveShoppableProducts(list: List<ShoppableProduct>) {
        shoppableRoomDAO.insertProducts(list)
    }

    override suspend fun fetchShoppableProductsLocally(): List<ShoppableProduct> {
        return shoppableRoomDAO.getAllProducts()
    }

    override suspend fun getShoppableProductByID(productID: Int): ShoppableProduct? {
        return shoppableRoomDAO.getProductById(productID)
    }

    override suspend fun saveBasketProduct(basketProduct: BasketProduct) {
        basketRoomDAO.insertProduct(basketProduct)
    }

    override suspend fun getBasketProducts(): List<BasketProduct> {
        return basketRoomDAO.getAllProducts()
    }

    override suspend fun deleteBasketProducts(basketProduct: BasketProduct) {
        basketRoomDAO.deleteProduct(basketProduct)
    }

    override suspend fun saveOrderProductsCross(orderProductCross: List<OrderProductCross>) {
        orderRoomDao.insertOrderProductCrossRefs(orderProductCross)
    }

    override suspend fun saveOrder(order: Order) {
        orderRoomDao.insertOrder(order)
    }

    override suspend fun saveOrderProducts(orderProducts: List<OrderProduct>) {
        orderProductRoomDao.insertOrderProducts(orderProducts)
    }

    override suspend fun getOrdersWithProducts(): List<OrderWithProducts> {
        return orderRoomDao.getOrdersWithProducts()
    }

    override suspend fun getOrderById(orderId: Int): Order {
        return orderRoomDao.getOrderById(orderId)
    }

    override suspend fun deleteOrderById(orderId: Int) {
        orderRoomDao.deleteOrder(orderId)
    }

    override suspend fun deleteAllBasketProducts() {
        basketRoomDAO.deleteAllProducts()
    }


}