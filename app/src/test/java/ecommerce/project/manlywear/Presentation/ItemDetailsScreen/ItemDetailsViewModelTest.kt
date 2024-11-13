package ecommerce.project.manlywear.Presentation.ItemDetailsScreen

import app.cash.turbine.test
import ecommerce.project.manlywear.Domain.Model.room.BasketProduct
import ecommerce.project.manlywear.Domain.Model.room.ShoppableProduct
import ecommerce.project.manlywear.Domain.UseCases.UseCasePack
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ItemDetailsViewModelTest {

    private lateinit var viewModel: ItemDetailsViewModel
    private val useCasePack = mockk<UseCasePack>(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = ItemDetailsViewModel(useCasePack)
    }

    @Test
    fun `test getShoppableProduct updates state`() = runTest {
        // Arrange
        val productId = 123
        val mockProduct = ShoppableProduct(productId = productId,
            title = "title",
            price = "23",
            category = "cat",
            description = "des",
            image = "image")
        coEvery { useCasePack.locallyFetchShoppableProduct.invoke(productId) } returns mockProduct

        // Act
        viewModel.getShoppableProduct(productId)

        // Assert
        // Collect state emissions using Turbine
        viewModel.shoppableproduct.test {
            val emittedProduct = awaitItem()
            assertEquals(mockProduct, emittedProduct)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test saveBasketProduct calls useCasePack`() = runTest {
        // Arrange
        val basketProduct = BasketProduct(productId = 1,
            "title",
            price = "123",
            category = "cat",
            description = "descr",
            image = "image",
            no_of_ordered_items = 2)

        // Act
        viewModel.saveBasketProduct(basketProduct)

        // Assert
        coVerify { useCasePack.saveBasketProduct.invoke(basketProduct) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}