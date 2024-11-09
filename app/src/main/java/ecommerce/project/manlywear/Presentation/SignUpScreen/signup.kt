package ecommerce.project.manlywear.Presentation.SignUpScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import ecommerce.project.manlywear.Presentation.Components.CustomButton
import ecommerce.project.manlywear.Presentation.Components.EditText
import ecommerce.project.manlywear.Presentation.Components.TopBar
import ecommerce.project.manlywear.R
import ecommerce.project.manlywear.ui.theme.ManlyWearTheme

@Composable
fun signupscreen(onclickback : () -> Unit,onsubmit: (email : String,password : String) -> Unit){
    ManlyWearTheme {
        var isvisible by remember {
            mutableStateOf(false)
        }

        LaunchedEffect(Unit) {
            isvisible = true
        }

        AnimatedVisibility(visible = isvisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 3000)),
            exit = fadeOut(animationSpec = tween(durationMillis = 3000))
        ) {
            Scaffold(topBar = {
                TopBar(modifier = Modifier, onclickback = { onclickback.invoke() })
            }) {paddingValues ->
                midSectionSignUp(paddingValues = paddingValues,onsubmit)
            }
        }
    }
}

@Composable
private fun midSectionSignUp(paddingValues: PaddingValues,onsubmit: (email : String,password : String) -> Unit){
    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = paddingValues.calculateTopPadding() + 16.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(96.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally){
            EditText(modifier = Modifier.fillMaxWidth(), ontype = {newtext ->
                email = newtext
            }, typedText = email, ispassword = false, hint = "Email")
            EditText(modifier = Modifier.fillMaxWidth(), ontype = {newpassword ->
                password = newpassword
            }, typedText = password, ispassword = true, hint = "Password")
        }

        Column (modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally){

            CustomButton(modifier = Modifier.fillMaxWidth(), onclick = { onsubmit.invoke(email, password)}, text = stringResource(
                id = R.string.sumbit
            )
            )

        }
    }
}