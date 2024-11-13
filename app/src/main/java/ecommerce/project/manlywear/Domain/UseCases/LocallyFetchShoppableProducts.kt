package ecommerce.project.manlywear.Domain.UseCases

import ecommerce.project.manlywear.Domain.Repository.Repository
import ecommerce.project.manlywear.Domain.Model.room.ShoppableProduct
import javax.inject.Inject

class LocallyFetchShoppableProducts @Inject constructor(val repository: Repository) {

    suspend operator fun invoke() : List<ShoppableProduct>{
        return repository.fetchShoppableProductsLocally()
    }
}