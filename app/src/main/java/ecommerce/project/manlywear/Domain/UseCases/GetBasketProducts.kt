package ecommerce.project.manlywear.Domain.UseCases

import ecommerce.project.manlywear.Domain.Model.room.BasketProduct
import ecommerce.project.manlywear.Domain.Repository.Repository
import javax.inject.Inject

class GetBasketProducts @Inject constructor(val repository: Repository) {

    suspend operator fun invoke() : List<BasketProduct>{
        return repository.getBasketProducts()
    }
}