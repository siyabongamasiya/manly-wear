package ecommerce.project.manlywear.Domain.UseCases

import ecommerce.project.manlywear.Domain.Model.relationModels.OrderProductCross
import ecommerce.project.manlywear.Domain.Model.room.Order
import ecommerce.project.manlywear.Domain.Model.room.OrderProduct
import ecommerce.project.manlywear.Domain.Repository.Repository
import javax.inject.Inject

class SaveOrder @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(order: Order,
                                orderproducts : List<OrderProduct>,
                                orderproductcross : List<OrderProductCross>){
        repository.saveOrder(order)
        repository.saveOrderProducts(orderproducts)
        repository.saveOrderProductsCross(orderproductcross)
        repository.deleteAllBasketProducts()
    }
}