package ecommerce.project.manlywear.Presentation.TrackOrderScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import ecommerce.project.manlywear.Constants.TRACKSCREEN_LIST
import ecommerce.project.manlywear.Domain.Model.room.Order
import ecommerce.project.manlywear.Presentation.Components.LevelStatus
import ecommerce.project.manlywear.Presentation.Components.TopBar
import ecommerce.project.manlywear.ui.theme.ManlyWearTheme


@Composable
fun trackorderscreen(orderId : Int,ongotohome: () -> Unit,trackOrderViewModel: TrackOrderViewModel){
    val order by trackOrderViewModel.order.collectAsState()

    LaunchedEffect (Unit){
        trackOrderViewModel.getOrderById(orderId)
    }

    ManlyWearTheme {
        Scaffold (topBar = {
            TopBar(modifier = Modifier, islight = false, onclickback = ongotohome)
        }){paddingValues ->
            midSectionTrackOrderScreen(paddingValues = paddingValues, order = order)
        }
    }
}

@Composable
fun midSectionTrackOrderScreen(paddingValues: PaddingValues,order : Order?){
    if (order != null) {
        LevelStatus(
            modifier = Modifier
                .testTag(TRACKSCREEN_LIST)
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding() + 16.dp),
            status = order.status
        )
    }
}