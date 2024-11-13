package ecommerce.project.manlywear.Presentation.OrderCompleteScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ecommerce.project.manlywear.Presentation.Components.Congratulator
import ecommerce.project.manlywear.Presentation.Components.CustomButton
import ecommerce.project.manlywear.R
import ecommerce.project.manlywear.ui.theme.ManlyWearTheme


@Composable
fun ordercompletescreen(ongotohome: () -> Unit, ongototrackorder: () -> Unit, orderId: Int){
    var isVisible by remember { mutableStateOf(false) }

    // Trigger visibility change after the Composable enters the composition
    LaunchedEffect(Unit) {
        isVisible = true
    }

    ManlyWearTheme {
        AnimatedVisibility(visible = isVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 2000)),
            exit = fadeOut(animationSpec = tween(durationMillis = 2000))
        ) {

            Scaffold {paddingvalues ->
                midSectionOrderComplete(paddingValues = paddingvalues,
                    ongototrackorder = ongototrackorder, ongotohome = ongotohome)
            }

        }
    }

}

@Composable
private fun midSectionOrderComplete(paddingValues: PaddingValues,
                            ongototrackorder : () -> Unit,
                            ongotohome : () -> Unit){

    Box(modifier =  Modifier.fillMaxSize()
        .padding(16.dp),
        contentAlignment = Alignment.Center){
        Column (modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)) {

            Congratulator(modifier = Modifier)
            CustomButton(modifier = Modifier,
                onclick = ongototrackorder,
                text = stringResource(id = R.string.gototrackorder))
            CustomButton(modifier = Modifier,
                onclick = ongotohome,
                text = stringResource(id = R.string.continue_shopping))

        }
    }


}