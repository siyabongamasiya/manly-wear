package ecommerce.project.manlywear.Domain.UseCases

import ecommerce.project.manlywear.Domain.Repository.Repository
import ecommerce.project.manlywear.Domain.Model.room.ShoppableProduct
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class RemoteFetchAndStoreShoppableProducts @Inject constructor(val repository: Repository) {

    suspend operator fun invoke() = runBlocking {
        //get remote products from api
        val retrofitproduct = repository.fetchProductsFromRemote()
        val shoppableproducts = mutableListOf<ShoppableProduct>()

        //store them in a list
        retrofitproduct?.forEach {remoteproduct ->
            val id = remoteproduct.id
            val title = remoteproduct.title
            val image = remoteproduct.image
            val category = remoteproduct.category
            val description = remoteproduct.description
            val price = remoteproduct.price

            shoppableproducts.add(ShoppableProduct(productId = id,
                title = title,
                price =price,
                category = category,
                description =  description,
                image =  image))
        }

        //save them locally
        repository.saveShoppableProducts(shoppableproducts)
    }
}