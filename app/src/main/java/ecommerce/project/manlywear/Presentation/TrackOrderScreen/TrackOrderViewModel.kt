package ecommerce.project.manlywear.Presentation.TrackOrderScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ecommerce.project.manlywear.Domain.Model.room.Order
import ecommerce.project.manlywear.Domain.Repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class TrackOrderViewModel @Inject constructor(private val repository: Repository) : ViewModel(){
    private var _order = MutableStateFlow<Order?>(null)
    val order = _order.asStateFlow()


    suspend fun getOrderById(orderId : Int){
        _order.value = repository.getOrderById(orderId)
    }
}