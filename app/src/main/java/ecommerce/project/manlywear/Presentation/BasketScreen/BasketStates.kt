package ecommerce.project.manlywear.Presentation.BasketScreen

sealed class BasketStates {
    data class OrderDetails(
        var deliveryAddress : String = "",
        var phoneNumber : String = "",
        var cardName : String = "",
        var cardNumber : String = "",
        var date : String = "",
        var totalCost : Double= 0.0,
        var ccv : String = "") : BasketStates()
}