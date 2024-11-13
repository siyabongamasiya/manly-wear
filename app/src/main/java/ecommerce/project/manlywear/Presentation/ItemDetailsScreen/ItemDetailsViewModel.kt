package ecommerce.project.manlywear.Presentation.ItemDetailsScreen

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ecommerce.project.manlywear.Domain.Model.room.BasketProduct
import ecommerce.project.manlywear.Domain.Model.room.ShoppableProduct
import ecommerce.project.manlywear.Domain.UseCases.UseCasePack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ItemDetailsViewModel @Inject constructor(private val useCasePack: UseCasePack) : ViewModel() {
    private var _shoppableProduct = MutableStateFlow<ShoppableProduct?>(null)
    val shoppableproduct = _shoppableProduct.asStateFlow()

    fun getShoppableProduct(productId : Int){
        viewModelScope.launch(Dispatchers.IO) {
            val shoppableProduct = useCasePack.locallyFetchShoppableProduct.invoke(productId)
            _shoppableProduct.value = shoppableProduct
        }
    }

    fun saveBasketProduct(basketProduct: BasketProduct){
        viewModelScope.launch {
            useCasePack.saveBasketProduct.invoke(basketProduct)
        }
    }
}