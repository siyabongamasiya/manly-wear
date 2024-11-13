package ecommerce.project.manlywear.Presentation.BasketScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ecommerce.project.manlywear.Domain.Model.room.BasketProduct
import ecommerce.project.manlywear.Presentation.Components.BasketItem
import ecommerce.project.manlywear.Presentation.Components.CustomButton
import ecommerce.project.manlywear.Presentation.Components.ModalBottomSheetWithCloseButton
import ecommerce.project.manlywear.Presentation.Components.TopBar
import ecommerce.project.manlywear.R
import ecommerce.project.manlywear.Utils.calculateTotalPrice
import ecommerce.project.manlywear.Utils.roundToTwoDecimalPlaces
import ecommerce.project.manlywear.ui.theme.ManlyWearTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun basketscreen(
    onclickback: () -> Unit,
    onviewitem: () -> Unit,
    ongotoordercomplete: () -> Unit,
    ondeletefrombasket: (basketProduct: BasketProduct) -> Unit,
    basketViewModel: BasketViewModel
) {
    ManlyWearTheme {
        val sheetState = rememberModalBottomSheetState()
        val scope = rememberCoroutineScope()

        val basketProducts by basketViewModel.basketItems.collectAsState()
        var totalCost = calculateTotalPrice(basketProducts).roundToTwoDecimalPlaces()
        basketViewModel.updateTotalCost(totalCost)

        LaunchedEffect(Unit) {
            basketViewModel.getBasketItems()
        }

        Scaffold(
            modifier = Modifier
                .imePadding()
                .navigationBarsPadding(),
            topBar = {
                TopBar(
                    modifier = Modifier,
                    onclickback = { onclickback.invoke() },
                    islight = false,
                    title = stringResource(id = R.string.Basket_Title)
                )
            },
            bottomBar = {
                bottomSectionBasketScreen(totalCost) {
                    scope.launch {
                        sheetState.show()
                    }
                }
            }
        ) { paddingValues ->
            midSectionBasketScreen(
                paddingValues = paddingValues,
                sheetState = sheetState,
                onclosesheet = {
                    scope.launch {
                        sheetState.hide()
                    }
                },
                ongotoordercomplete = ongotoordercomplete,
                onviewitem = onviewitem,
                ondeleteproduct = ondeletefrombasket,
                basketitems = basketProducts,
                basketViewModel = basketViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun midSectionBasketScreen(
    paddingValues: PaddingValues,
    onviewitem: () -> Unit,
    sheetState: SheetState,
    onclosesheet: () -> Unit,
    ongotoordercomplete: () -> Unit,
    ondeleteproduct: (basketProduct: BasketProduct) -> Unit,
    basketitems: List<BasketProduct>,
    basketViewModel: BasketViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding() + 16.dp,
                bottom = paddingValues.calculateBottomPadding() + 16.dp
            )
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(basketitems) { item ->
            BasketItem(
                modifier = Modifier,
                itemname = item.title,
                itempic = item.image,
                numberOrdered = item.no_of_ordered_items,
                itemPrice = item.price,
                ondeletefrombasket = { ondeleteproduct.invoke(item) },
                onclick = onviewitem
            )
        }
    }

    ModalBottomSheetWithCloseButton(
        sheetState = sheetState,
        modifier = Modifier.padding(bottom = paddingValues.calculateTopPadding() + 16.dp),
        ongotoordercomplete = ongotoordercomplete,
        onclose = onclosesheet,
        basketViewModel = basketViewModel
    )
}

@Composable
private fun bottomSectionBasketScreen(
    totalCosts : Double,
    onopensheet: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .navigationBarsPadding(), // Ensures bottom section is above nav controls
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(0.4f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total",
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "R${totalCosts}",
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.titleLarge
            )
        }

        CustomButton(
            modifier = Modifier.weight(0.6f),
            onclick = { onopensheet.invoke() },
            text = stringResource(id = R.string.basket_button_title)
        )
    }
}



