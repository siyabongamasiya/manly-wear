package ecommerce.project.manlywear.Presentation.OrdersScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ecommerce.project.manlywear.Domain.Model.room.RoomOrder
import ecommerce.project.manlywear.Presentation.Components.OrderItem
import ecommerce.project.manlywear.Presentation.Components.TopBar
import ecommerce.project.manlywear.R
import ecommerce.project.manlywear.ui.theme.ManlyWearTheme
import kotlinx.coroutines.launch


@Composable
fun ordersscreen(onclickback : () -> Unit,
                 onvieworder: () -> Unit,
                 ondeleteorder: () -> Unit){

    ManlyWearTheme {
        var isvisible by remember {
            mutableStateOf(false)
        }

        LaunchedEffect(Unit) {
            isvisible = true
        }

        AnimatedVisibility(visible = isvisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 2000)),
            exit = fadeOut(animationSpec = tween(durationMillis = 2000))
        ) {
            Scaffold(topBar = {
                TopBar(modifier = Modifier, onclickback = onclickback, islight = false, title = stringResource(
                    id = R.string.Orders_Title)
                )
            }) {paddingValues ->
                midSectionOrdersScreen(
                    paddingValues = paddingValues,
                    onvieworder = onvieworder,
                    ondeleteorder = ondeleteorder)
            }
        }


    }
}

@Composable
private fun midSectionOrdersScreen(paddingValues: PaddingValues,
                                   onvieworder : () -> Unit,
                                   ondeleteorder : () -> Unit){



    val list = listOf(RoomOrder(3455,"siyabonga",
        3,
        5009,
        "Uzimazane cres",
        "0749 30003","siyabonga"
        ,"3444","date"
        ,"899"),
        RoomOrder(5543,"siyabonga",
            2,
            5009,
            "Uzimazane cres",
            "0749 30003","siyabonga"
            ,"3444","date"
            ,"899"),
        RoomOrder(9888,"siyabonga",
            2,
            5009,
            "Uzimazane cres",
            "0749 30003","siyabonga"
            ,"3444","date"
            ,"899"))
    val items by remember {
        mutableStateOf(list)
    }

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(top = paddingValues.calculateTopPadding() + 16.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {

        items(items){order ->
            OrderItem(modifier = Modifier,
                orderID = order.orderId.toString(),
                orderTotal = order.totalCost,
                ondeleteOrder = ondeleteorder,
                onclick = onvieworder)
        }

    }

}