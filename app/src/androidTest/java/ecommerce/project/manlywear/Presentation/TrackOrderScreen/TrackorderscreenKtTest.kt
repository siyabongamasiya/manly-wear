package ecommerce.project.manlywear.Presentation.TrackOrderScreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import ecommerce.project.manlywear.Constants.TRACKSCREEN_LIST
import ecommerce.project.manlywear.Domain.Model.room.Order
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MidSectionTrackOrderScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysLevelStatusWhenOrderIsNotNull() {
        // Arrange: Create an Order with a sample status and padding
        val order = Order(status = 2,
            orderId = 1,
            userId = "user",
            totalCost = 0.0,
            deliveryAddress = "",
            phoneNumber = "",
            cardNumber = "",
            cardHolder = "",
            ccv = "",
            date = "")
        val paddingValues = PaddingValues(8.dp)

        // Act: Render the composable with a non-null Order
        composeTestRule.setContent {
            midSectionTrackOrderScreen(paddingValues = paddingValues, order = order)
        }

        // Assert: Check if the tracker is displayed
        composeTestRule.onNodeWithTag(TRACKSCREEN_LIST).assertIsDisplayed()
    }

    @Test
    fun doesNotDisplayLevelStatusWhenOrderIsNull() {
        // Arrange: Define padding but pass null for the Order
        val paddingValues = PaddingValues(8.dp)

        // Act: Render the composable with a null Order
        composeTestRule.setContent {
            midSectionTrackOrderScreen(paddingValues = paddingValues, order = null)
        }

        // Assert: Ensure tracker not displayed
        composeTestRule.onNodeWithTag(TRACKSCREEN_LIST).assertIsNotDisplayed()
    }
}