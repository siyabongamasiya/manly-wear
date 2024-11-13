package ecommerce.project.manlywear.Presentation.BasketScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import ecommerce.project.manlywear.Domain.Model.room.BasketProduct
import ecommerce.project.manlywear.Domain.Model.room.Order
import ecommerce.project.manlywear.Domain.Repository.AuthRepository
import ecommerce.project.manlywear.Domain.UseCases.DeleteBasketProduct
import ecommerce.project.manlywear.Domain.UseCases.SaveOrder
import ecommerce.project.manlywear.Domain.UseCases.UseCasePack
import ecommerce.project.manlywear.Utils.createCrossList
import ecommerce.project.manlywear.Utils.generateUniqueOrderId
import ecommerce.project.manlywear.WorkManager.OrderScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BasketViewModel @Inject constructor(private val useCasePack: UseCasePack,
                                          private val orderscheduler : OrderScheduler,
                                          private val authRepository: AuthRepository) : ViewModel(){

    private var _basketItems = MutableStateFlow<List<BasketProduct>>(emptyList())
    val basketItems = _basketItems.onStart {
        getBasketItems()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private var _orderDetails = MutableStateFlow(BasketStates.OrderDetails())
    val orderDetails = _orderDetails.asStateFlow()

    suspend fun getBasketItems(){
        val basketproducts = useCasePack.getBasketProducts.invoke()
        _basketItems.value = basketproducts
    }

    fun deleteBasketProduct(basketProduct: BasketProduct){
        viewModelScope.launch {
            useCasePack.deleteBasketProduct.invoke(basketProduct)
            getBasketItems()
        }
    }

    fun SaveOrder(context: Context) : Int{
        //create new ID and Order
        val newId = generateUniqueOrderId()
        val order = Order(
            orderId = newId,
            userId = authRepository.getCurrentUser()?.email ?: "masiya",
            totalCost = _orderDetails.value.totalCost,
            deliveryAddress = _orderDetails.value.deliveryAddress,
            phoneNumber = _orderDetails.value.phoneNumber,
            cardHolder = _orderDetails.value.cardName,
            cardNumber = _orderDetails.value.cardNumber,
            date = _orderDetails.value.date,
            ccv = _orderDetails.value.ccv)

        //create pair of orders and cross Reference classes
        val pair = createCrossList(_basketItems.value,newId)

        //get orders and cross classes from pair
        val orders = pair.first
        val crossRefs = pair.second


        //save Order
        viewModelScope.launch {
            useCasePack.saveOrder.invoke(order,orders,crossRefs)
        }

        //imitate backend worker with work manager
        orderscheduler.scheduleTask(newId)

        return newId
    }

    fun updateTotalCost(totalCost : Double){
        _orderDetails.value = _orderDetails.value.copy(totalCost =  totalCost)
    }
    fun updateDeliveryDetails(deliveryAddress : String?,phoneNumber : String?){
        _orderDetails.value = _orderDetails.value.copy(deliveryAddress = deliveryAddress ?: _orderDetails.value.deliveryAddress)
        _orderDetails.value = _orderDetails.value.copy(phoneNumber = phoneNumber ?: _orderDetails.value.phoneNumber)
    }

    fun updateCardDetails(cardName : String?,cardNumber : String?,date : String?,ccv : String?){
        _orderDetails.value = _orderDetails.value.copy( cardName = cardName ?: _orderDetails.value.cardName)
        _orderDetails.value = _orderDetails.value.copy(cardNumber = cardNumber ?: _orderDetails.value.cardNumber)
        _orderDetails.value = _orderDetails.value.copy( date = date ?: _orderDetails.value.date)
        _orderDetails.value = _orderDetails.value.copy(ccv = ccv ?: _orderDetails.value.ccv)
    }
}