package ecommerce.project.manlywear.Presentation.HomeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ecommerce.project.manlywear.Constants.NetworkStates
import ecommerce.project.manlywear.Domain.Model.room.ShoppableProduct
import ecommerce.project.manlywear.Domain.UseCases.UseCasePack
import ecommerce.project.manlywear.Utils.EventDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val useCasePack: UseCasePack) : ViewModel() {
    private var _shoppableProducts = MutableStateFlow<List<ShoppableProduct>>(emptyList())
    val shoppableProducts = _shoppableProducts.onStart {
        getShoppableProducts()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    fun getShoppableProducts(){

        viewModelScope.launch(Dispatchers.IO) {
            EventDispatcher.sendNetworkStatus(NetworkStates.INPROGRESS.name)
            val shoppableProducts = useCasePack.locallyFetchShoppableProducts.invoke()
            if (shoppableProducts.isEmpty()){
                useCasePack.remoteFetchAndStoreShoppableProducts.invoke()
                val notemptyshoppableproducts = useCasePack.locallyFetchShoppableProducts.invoke()

                withContext(Dispatchers.Main){
                    _shoppableProducts.value = notemptyshoppableproducts
                    EventDispatcher.sendNetworkStatus(NetworkStates.DONE.name)
                }

            }else{
                withContext(Dispatchers.Main){
                    _shoppableProducts.value = shoppableProducts
                    EventDispatcher.sendNetworkStatus(NetworkStates.DONE.name)
                }
            }
        }
    }
}