package ecommerce.project.manlywear.Domain.UseCases

import ecommerce.project.manlywear.Domain.Repository.Repository
import ecommerce.project.manlywear.Domain.Model.room.ShoppableProduct
import javax.inject.Inject

class LocallyFetchShoppableProduct @Inject constructor(val repository: Repository) {

    suspend operator fun invoke(productID : Int) : ShoppableProduct?{
        return repository.getShoppableProductByID(productID)
    }
}