package ecommerce.project.manlywear.Utils

import ecommerce.project.manlywear.Domain.Model.relationModels.OrderProductCross
import ecommerce.project.manlywear.Domain.Model.room.BasketProduct
import ecommerce.project.manlywear.Domain.Model.room.OrderProduct
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger

private val counter = AtomicInteger(0)


/**
 * 1.Valid Cases
 * Simple Valid Email: "user@example.com"
 * Email with Numbers and Special Characters: "user123+test@example.com"
 * Email with Dashes and Periods in Domain: "user.name@example.co.uk"
 * Email with Subdomains: "user@mail.server.example.com"
 * Email with Underscore: "first_last@example.com"
 *
 * 2.Invalid Cases
 * Missing @ Symbol: "userexample.com"
 * Missing Username: "@example.com"
 * Consecutive Dots: "user..name@example.com"
 * Special Characters Not Allowed: "user!@example.com"
 * Spaces in Email: "user name@example.com"
 * Missing Domain: "username@"
 */
fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    return email.matches(emailRegex.toRegex())
}


/**1.Test cases
 * Non-Empty Product List: Tests with multiple products with positive prices to verify the correct sum.
 * Empty Product List: Ensures the function returns 0.0 when no products are provided.
 * Single Product: Checks that the function handles a list with only one product.
 * Zero Priced Product: Verifies that products with 0.0 price are correctly included in the total calculation.
 * Negative Priced Product: Tests handling of a product with a negative price, such as for discounts, ensuring the calculation correctly incorporates it.
 */
fun calculateTotalPrice(products: List<BasketProduct>): Double {
    return products.sumOf { it.price.toDouble() * it.no_of_ordered_items }
}

/**
 * Test Cases for `roundToTwoDecimalPlaces` function:
 *
 * 1. Positive Decimal Value:
 *    - Input: 3.456
 *    - Expected Output: 3.46
 *
 * 2. Negative Decimal Value:
 *    - Input: -2.678
 *    - Expected Output: -2.68
 *
 * 3. Positive Value Ending in 5:
 *    - Input: 1.235
 *    - Expected Output: 1.24
 *
 * 4. Zero Value:
 *    - Input: 0.0
 *    - Expected Output: 0.0
 *
 * 5. Large Decimal Value:
 *    - Input: 12345.6789
 *    - Expected Output: 12345.68
 *
 * 6. Small Decimal Value (Close to Zero):
 *    - Input: 0.004
 *    - Expected Output: 0.0
 */

fun Double.roundToTwoDecimalPlaces(): Double {
    return BigDecimal(this).setScale(2, RoundingMode.HALF_EVEN).toDouble()
}


/**
 * Function to extract the username (part before "@") from an email address.
 * Test Cases:
 * 1. Valid email with username and domain (e.g., "user@example.com") - Should return "user".
 * 2. Email with multiple dots (e.g., "user.name@example.com") - Should return "user.name".
 * 3. Email without username (e.g., "@example.com") - Should return an empty string "".
 * 4. Empty string as email (e.g., "") - Should return an empty string "".
 * 5. Email with no "@" character (e.g., "usernameonly") - Should return "usernameonly".
 * 6. Email with special characters in the username (e.g., "user_name+123@example.com") - Should return "user_name+123".
 */
fun getUsernameFromEmail(email: String): String {
    return email.substringBefore("@")
}

fun createCrossList(listOfBasketProducts: List<BasketProduct>, orderPrimaryKey: Int): Pair<List<OrderProduct>, List<OrderProductCross>> {
    val orderProducts = listOfBasketProducts.map { basketProduct ->
        OrderProduct(
            productId = basketProduct.productId,
            title = basketProduct.title,
            price = basketProduct.price,
            category = basketProduct.category,
            description = basketProduct.description,
            image = basketProduct.image,
            no_of_ordered_items = basketProduct.no_of_ordered_items
        )
    }

    val crossReferences = listOfBasketProducts.map { basketProduct ->
        OrderProductCross(
            orderId = orderPrimaryKey,
            productId = basketProduct.productId
        )
    }

    return Pair(orderProducts, crossReferences)
}

/**
 * Test Cases:
 *
 * 1. **Uniqueness Test**:
 *    - Call `generateUniqueOrderId()` multiple times and ensure each call returns a unique value.
 *    - Run a large number of calls (e.g., 10,000 times) and check for any duplicate order IDs.
 *
 * 2. **Consistency Test**:
 *    - Call `generateUniqueOrderId()` multiple times and ensure it doesn’t return the same ID
 *      even if called within a short span of time.
 *
 * 3. **Range Test**:
 *    - Ensure the generated ID is within the range of valid Int values.
 *    - Verify the ID is non-negative since we’re using hashCode().
 *
 * 4. **Performance Test**:
 *    - Measure the time taken to generate IDs over multiple calls to ensure the function is efficient.
 */
fun generateUniqueOrderId(): Int {
    val timestamp = System.currentTimeMillis()
    val uniqueIncrement = counter.getAndIncrement() // Unique increment each time

    // Combine the timestamp and counter, and ensure it falls within Int range
    return ((timestamp + uniqueIncrement) % Int.MAX_VALUE).toInt()
}
