package ecommerce.project.manlywear.Presentation.SplashScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ecommerce.project.manlywear.R
import ecommerce.project.manlywear.ui.theme.ManlyWearTheme
//

@Composable
fun splashscreen(ongotowelcomescreen : () -> Unit) {
    val navHostController = rememberNavController()
    ManlyWearTheme {
        Scaffold {paddingvalues ->
            midSectionSplashScreen(paddingValues = paddingvalues,ongotowelcomescreen = ongotowelcomescreen)
        }
    }

}

@Composable
fun midSectionSplashScreen(paddingValues: PaddingValues,ongotowelcomescreen: () -> Unit){
    // Animatable value to control the alpha (opacity)
    val alpha = remember { Animatable(0f) }

    // Launch animation to increase the alpha value to 1 over 3 seconds
    LaunchedEffect(Unit) {
        val animationResult = alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 8000)
        )

        // Check if the animation completed successfully
        if (animationResult.endReason == AnimationEndReason.Finished) {
            ongotowelcomescreen.invoke()
        }
    }

    Box(modifier = Modifier.fillMaxSize()
        .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center) {

        Image(
            modifier = Modifier
                .size(200.dp)
                .alpha(alpha.value),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo")

    }
}
