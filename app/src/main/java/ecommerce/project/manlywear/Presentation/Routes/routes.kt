package ecommerce.project.manlywear.Presentation.Routes

import ecommerce.project.manlywear.Domain.Model.room.RoomProduct
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
    data class Home(val arg: String = "") : Routes()

    @Serializable
    data class ItemDetailsScreen(val roomProduct: String = "") : Routes()

    @Serializable
    data class BasketScreen(val arg: String = "") : Routes()

    @Serializable
    data class OrdersScreen(val arg: String = "") : Routes()

    @Serializable
    data class OrderCompleteScreen(val arg: String = "") : Routes()

    @Serializable
    data class TrackOrderScreen(val arg: String = "") : Routes()
}