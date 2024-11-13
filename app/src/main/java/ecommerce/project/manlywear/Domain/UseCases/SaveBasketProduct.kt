package ecommerce.project.manlywear.Domain.UseCases

import ecommerce.project.manlywear.Domain.Model.room.BasketProduct
import ecommerce.project.manlywear.Domain.Repository.Repository
import javax.inject.Inject

class SaveBasketProduct @Inject constructor(val repository: Repository) {

    operator suspend fun invoke(basketProduct: BasketProduct){
        repository.saveBasketProduct(basketProduct)
    }
}