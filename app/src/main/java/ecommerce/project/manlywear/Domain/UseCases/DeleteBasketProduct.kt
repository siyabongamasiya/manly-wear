package ecommerce.project.manlywear.Domain.UseCases

import ecommerce.project.manlywear.Domain.Model.room.BasketProduct
import ecommerce.project.manlywear.Domain.Repository.Repository
import javax.inject.Inject

class DeleteBasketProduct @Inject constructor(private val repository: Repository){

    suspend operator fun invoke(basketProduct: BasketProduct){
        repository.deleteBasketProducts(basketProduct)
    }
}