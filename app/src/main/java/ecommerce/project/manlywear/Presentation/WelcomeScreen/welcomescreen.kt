package ecommerce.project.manlywear.Presentation.WelcomeScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ecommerce.project.manlywear.Presentation.Components.CustomButton
import ecommerce.project.manlywear.Presentation.Routes.Routes
import ecommerce.project.manlywear.R
import ecommerce.project.manlywear.ui.theme.ManlyWearTheme


@Composable
fun welcomescreen(onPressLogin : () -> Unit) {
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

            Scaffold {paddingValues ->
                midSectionWelcomeScreen(paddingValues = paddingValues,
                    onPressLogin = onPressLogin)
            }

        }
    }
}

@Composable
fun midSectionWelcomeScreen(paddingValues: PaddingValues,
                            onPressLogin: () -> Unit){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = paddingValues.calculateTopPadding())
        .background(MaterialTheme.colorScheme.primary)) {
        Box (modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiary),
            contentAlignment = Alignment.Center){
            
            Image(
                modifier = Modifier.size(width = 301.dp, height = 260.dp),
                painter = painterResource(id = R.drawable.welcomeimage),
                contentDescription = "welcome image")
            
        }
        
        Column(modifier = Modifier
            .weight(1f)
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Column (modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally){

                Text(text = stringResource(id = R.string.welcome_title),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge)
                Text(text = stringResource(id = R.string.welcome_Description),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge)

            }

            CustomButton(modifier = Modifier.fillMaxWidth(), onclick = { onPressLogin.invoke()}, text = stringResource(
                id = R.string.continue_welcome
            ))
        }


    }
}