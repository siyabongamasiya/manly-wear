package ecommerce.project.manlywear.Presentation.BasketScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ecommerce.project.manlywear.Domain.Model.room.RoomProduct
import ecommerce.project.manlywear.Presentation.Components.BasketItem
import ecommerce.project.manlywear.Presentation.Components.CustomButton
import ecommerce.project.manlywear.Presentation.Components.ModalBottomSheetWithCloseButton
import ecommerce.project.manlywear.Presentation.Components.TopBar
import ecommerce.project.manlywear.R
import ecommerce.project.manlywear.ui.theme.ManlyWearTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun basketscreen(onclickback : () -> Unit,
                 onviewitem: () -> Unit,
                 ongotoordercomplete : () -> Unit,
                 ondeletefrombasket : () -> Unit){
    ManlyWearTheme {
        val sheetState = rememberModalBottomSheetState()
        val scope = rememberCoroutineScope()

        Scaffold(topBar = {
            TopBar(modifier = Modifier, onclickback = {onclickback.invoke()}, islight = false, title = stringResource(
                id = R.string.Basket_Title
            ))
        }, bottomBar = {
            bottomSectionBasketScreen{
                scope.launch {
                    sheetState.show()
                }
            }
        }) {paddingValues ->
            midSectionBasketScreen(paddingValues = paddingValues,sheetState=sheetState, onclosesheet = {scope.launch {
                sheetState.hide()
            }},
                ongotoordercomplete = ongotoordercomplete,
                onviewitem = { onviewitem.invoke() }) {
                ondeletefrombasket.invoke()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun midSectionBasketScreen(paddingValues: PaddingValues,
                                   onviewitem : () -> Unit,
                                   sheetState: SheetState,
                                   onclosesheet : () -> Unit,
                                   ongotoordercomplete: () -> Unit,
                                   ondeleteproduct : () -> Unit){



    val list = listOf(RoomProduct(1,
        "watch",
        "5009",
        "men's",
        "Stayela Wear watch by Stayela company!!",
        "k",
        2,
        4),
        RoomProduct(1,
            "watch",
            "5009",
            "men's",
            "Stayela Wear watch by Stayela company!!",
            "k",
            2,
            4),
        RoomProduct(1,
            "watch",
            "5009",
            "men's",
            "Stayela Wear watch by Stayela company!!",
            "k",
            2,
            4),
        RoomProduct(1,
            "watch",
            "5009",
            "men's",
            "Stayela Wear watch by Stayela company!!",
            "k",
            3,
            4),
        RoomProduct(1,
            "watch",
            "5009",
            "men's",
            "Stayela Wear watch by Stayela company!!",
            "k",
            2,
            4))
    val items by remember {
        mutableStateOf(list)
    }

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(top = paddingValues.calculateTopPadding() + 16.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {

        items(items){item ->
            BasketItem(modifier = Modifier,
                itemname = item.title,
                itempic = item.image,
                numberOrdered = item.no_of_ordered_items,
                itemPrice = item.price,
                ondeletefrombasket = ondeleteproduct) {

                onviewitem.invoke()

            }
        }

    }

    ModalBottomSheetWithCloseButton(sheetState = sheetState,
        ongotoordercomplete = ongotoordercomplete,
        onclose = onclosesheet)

}

@Composable
private fun bottomSectionBasketScreen(onopensheet : () -> Unit){
    val total by remember {
        mutableStateOf(234)
    }

    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)){
        Column(modifier = Modifier.weight(0.4f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = "Total", color = Color.Black, style = MaterialTheme.typography.titleMedium)
            Text(text = "R${total}", color = MaterialTheme.colorScheme.tertiary, style = MaterialTheme.typography.titleLarge)

        }

        CustomButton(modifier = Modifier.weight(0.6f),
            onclick = { onopensheet.invoke()},
            text = stringResource(id = R.string.basket_button_title))
    }
}