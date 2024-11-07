package ecommerce.project.manlywear.Presentation.LoginScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ecommerce.project.manlywear.Presentation.Components.CustomButton
import ecommerce.project.manlywear.Presentation.Components.EditText
import ecommerce.project.manlywear.R
import ecommerce.project.manlywear.ui.theme.ManlyWearTheme



@Composable
fun loginscreen(onlogin : () -> Unit,onsignup : () -> Unit){
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
            Scaffold {paddingValues ->
                midSectionLogin(paddingValues,onlogin,onsignup)
            }
        }
    }
}

@Composable
private fun midSectionLogin(paddingValues: PaddingValues,onlogin: () -> Unit,onsignup: () -> Unit){
    var username by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.fillMaxSize()
        .padding(top = paddingValues.calculateTopPadding() + 16.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(96.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally){
            EditText(modifier = Modifier.fillMaxWidth(), ontype = {newtext ->
                username = newtext
            }, typedText = username, ispassword = false,hint = "Username")
            EditText(modifier = Modifier.fillMaxWidth(), ontype = {newpassword ->
                password = newpassword
            }, typedText = password, ispassword = true, hint = "Password")
        }

        Column (modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally){

            CustomButton(modifier = Modifier.fillMaxWidth(), onclick = { onlogin.invoke() }, text = stringResource(
                id = R.string.login
            ))

            CustomButton(modifier = Modifier.fillMaxWidth(), onclick = { onsignup.invoke() }, text = stringResource(
                id = R.string.signup
            ))

        }
    }
}