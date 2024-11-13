package ecommerce.project.manlywear.Domain.UseCases

import javax.inject.Inject

data class UseCasePack @Inject constructor(
    val locallyFetchShoppableProducts: LocallyFetchShoppableProducts,
    val remoteFetchAndStoreShoppableProducts: RemoteFetchAndStoreShoppableProducts,
    val locallyFetchShoppableProduct: LocallyFetchShoppableProduct,
    val saveBasketProduct: SaveBasketProduct,
    val getBasketProducts: GetBasketProducts,
    val deleteBasketProduct: DeleteBasketProduct,
    val saveOrder: SaveOrder
)