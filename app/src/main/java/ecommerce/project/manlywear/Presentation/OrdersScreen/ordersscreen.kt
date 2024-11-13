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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ecommerce.project.manlywear.Domain.Model.relationModels.OrderWithProducts
import ecommerce.project.manlywear.Domain.Model.room.Order
import ecommerce.project.manlywear.Presentation.Components.OrderItem
import ecommerce.project.manlywear.Presentation.Components.TopBar
import ecommerce.project.manlywear.R
import ecommerce.project.manlywear.Utils.roundToTwoDecimalPlaces
import ecommerce.project.manlywear.ui.theme.ManlyWearTheme


@Composable
fun ordersscreen(onclickback : () -> Unit,
                 onvieworder: (orderId : Int) -> Unit,
                 ondeleteorder : (orderId : Int) -> Unit,
                 ordersViewModel: OrdersViewModel){

    ManlyWearTheme {
        var isvisible by remember {
            mutableStateOf(false)
        }

        val orderswithproducts by ordersViewModel.orderWithProducts.collectAsState()

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
                    ondeleteorder = ondeleteorder,
                    orderswithproducts = orderswithproducts
                    )
            }
        }


    }
}

@Composable
private fun midSectionOrdersScreen(paddingValues: PaddingValues,
                                   onvieworder : (orderId : Int) -> Unit,
                                   ondeleteorder : (orderId : Int) -> Unit,
                                   orderswithproducts : List<OrderWithProducts>){


    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(top = paddingValues.calculateTopPadding() + 16.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {

        items(orderswithproducts){orderwithproduct ->
            OrderItem(modifier = Modifier,
                orderID = orderwithproduct.order.orderId.toString(),
                orderTotal = orderwithproduct.order.totalCost.roundToTwoDecimalPlaces(),
                ondeleteOrder = {ondeleteorder.invoke(orderwithproduct.order.orderId)},
                onclick = {
                    onvieworder.invoke(orderwithproduct.order.orderId)
                })
        }

    }

}