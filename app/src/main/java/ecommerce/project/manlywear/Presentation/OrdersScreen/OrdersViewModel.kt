package ecommerce.project.manlywear.Presentation.OrdersScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ecommerce.project.manlywear.Domain.Model.relationModels.OrderWithProducts
import ecommerce.project.manlywear.Domain.Model.room.Order
import ecommerce.project.manlywear.Domain.Repository.Repository
import ecommerce.project.manlywear.Domain.UseCases.UseCasePack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private var _ordersWithProducts = MutableStateFlow<List<OrderWithProducts>>(emptyList())
    val orderWithProducts = _ordersWithProducts.onStart {
        getOrdersWithProducts()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    suspend fun getOrdersWithProducts(){
        _ordersWithProducts.value = repository.getOrdersWithProducts()
    }

    fun deleteOrder(orderId : Int){
        viewModelScope.launch {
            repository.deleteOrderById(orderId)
            getOrdersWithProducts()
        }
    }
}