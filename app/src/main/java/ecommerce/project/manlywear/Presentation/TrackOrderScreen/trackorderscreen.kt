package ecommerce.project.manlywear.Presentation.TrackOrderScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ecommerce.project.manlywear.Domain.Model.room.RoomOrder
import ecommerce.project.manlywear.Presentation.Components.LevelStatus
import ecommerce.project.manlywear.Presentation.Components.TopBar
import ecommerce.project.manlywear.ui.theme.ManlyWearTheme


@Composable
fun trackorderscreen(order : String,ongotohome: () -> Unit){
    val dummyorder = RoomOrder(1,
        "user",
        4,
        3500,
        "mna",
        "0900",
        "dkdk",
        "3444",
        "date",
        "222")

    ManlyWearTheme {
        Scaffold (topBar = {
            TopBar(modifier = Modifier, islight = false, onclickback = ongotohome)
        }){paddingValues ->
            midSectionTrackOrderScreen(paddingValues = paddingValues, order = dummyorder)
        }
    }
}

@Composable
private fun midSectionTrackOrderScreen(paddingValues: PaddingValues,order : RoomOrder){
    LevelStatus(modifier = Modifier
        .background(MaterialTheme.colorScheme.primary)
        .fillMaxSize()
        .padding(top = paddingValues.calculateTopPadding() + 16.dp), status = order.status)
}