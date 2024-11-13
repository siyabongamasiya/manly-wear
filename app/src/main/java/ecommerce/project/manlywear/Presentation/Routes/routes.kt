package ecommerce.project.manlywear.Presentation.Routes

import ecommerce.project.manlywear.Domain.Model.room.ShoppableProduct
import kotlinx.serialization.Serializable

sealed class Routes{

    @Serializable
    object Splash

    @Serializable
    data class Welcome(val arg : String = "") : Routes()

    @Serializable
    data class Login(val arg : String = "") : Routes()

    @Serializable
    data class SignUp(val arg : String = "") : Routes()

    @Serializable
    data class Home(val username: String = "") : Routes()

    @Serializable
    data class ItemDetailsScreen(val shoppableProductID: Int = 0) : Routes()

    @Serializable
    data class BasketScreen(val arg: String = "") : Routes()

    @Serializable
    data class OrdersScreen(val arg: String = "") : Routes()

    @Serializable
    data class OrderCompleteScreen(val orderId : Int) : Routes()

    @Serializable
    data class TrackOrderScreen(val orderId : Int) : Routes()
}