package ecommerce.project.manlywear

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import ecommerce.project.manlywear.Constants.INVALID
import ecommerce.project.manlywear.Constants.INVALID_DETAILS_LOGIN
import ecommerce.project.manlywear.Constants.INVALID_DETAILS_SIGN_UP
import ecommerce.project.manlywear.Constants.INVALID_EMAIL_FORMAT
import ecommerce.project.manlywear.Constants.INVALID_USER
import ecommerce.project.manlywear.Constants.NetworkStates
import ecommerce.project.manlywear.Firebase.AuthRepositoryImpl
import ecommerce.project.manlywear.Presentation.BasketScreen.basketscreen
import ecommerce.project.manlywear.Presentation.HomeScreen.homescreen
import ecommerce.project.manlywear.Presentation.ItemDetailsScreen.itemdetailsscreen
import ecommerce.project.manlywear.Presentation.LoginScreen.LoginViewModel
import ecommerce.project.manlywear.Presentation.LoginScreen.loginscreen
import ecommerce.project.manlywear.Presentation.OrderCompleteScreen.ordercompletescreen
import ecommerce.project.manlywear.Presentation.OrdersScreen.ordersscreen
import ecommerce.project.manlywear.Presentation.Routes.Routes
import ecommerce.project.manlywear.Presentation.SignUpScreen.SignUpViewModel
import ecommerce.project.manlywear.Presentation.SignUpScreen.signupscreen
import ecommerce.project.manlywear.Presentation.SplashScreen.SplashViewModel
import ecommerce.project.manlywear.Presentation.SplashScreen.splashscreen
import ecommerce.project.manlywear.Presentation.TrackOrderScreen.trackorderscreen
import ecommerce.project.manlywear.Presentation.WelcomeScreen.welcomescreen
import ecommerce.project.manlywear.Utils.EventDispatcher
import ecommerce.project.manlywear.Utils.SharedPreferencesUtil
import ecommerce.project.manlywear.Utils.isValidEmail
import ecommerce.project.manlywear.ui.theme.ManlyWearTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var auth : AuthRepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            ManlyWearTheme {
                // Create a SnackbarHostState
                val snackbarHostState = remember { SnackbarHostState() }
                // Coroutine scope to show the snackbar
                val scope = rememberCoroutineScope()
                var showloading by remember {
                    mutableStateOf(false)
                }

                LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {

                    scope.launch {

                        EventDispatcher.networkStatus.collect{result ->
                            showloading = result == NetworkStates.INPROGRESS.name
                        }
                    }

                }

                Scaffold(
                    // Attach the SnackbarHostState to the Scaffold
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) { paddingValues ->

                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding() + 16.dp),
                        contentAlignment = Alignment.Center){
                        App(navHostController = rememberNavController(),onshowsnackbar = {message->
                            scope.launch {
                                snackbarHostState.showSnackbar(message)
                            }
                        })

                        if (showloading) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.tertiary)
                        }

                    }




                }

            }
        }
    }


    @Composable
    fun App(navHostController: NavHostController,onshowsnackbar : (message : String) -> Unit){
        val splashViewModel = hiltViewModel<SplashViewModel>()
        val loginViewModel = hiltViewModel<LoginViewModel>()
        val signUpViewModel = hiltViewModel<SignUpViewModel>()
        val coroutine = rememberCoroutineScope()

        val context = LocalContext.current

        NavHost(navController = navHostController, startDestination = Routes.Splash) {
            composable<Routes.Splash>{
                splashscreen(ongotowelcomescreen = {
                    val useremail = SharedPreferencesUtil.getEmail(context)
                    val password = SharedPreferencesUtil.getPassword(context)

                    //if logged in go to home,
                    if(auth.getCurrentUser() != null) {
                        navHostController.navigate(Routes.Home()) {
                            popUpTo(0)
                        }
                    }else{
                        //else check if first time
                        if (useremail != null && password != null) {
                            //sign in with existing details if not first time
                            coroutine.launch {
                                val result = splashViewModel.SignInUser(useremail, password)
                                result.onSuccess {
                                    navHostController.navigate(Routes.Home())
                                }

                                result.onFailure { _ ->
                                    navHostController.navigate(Routes.Login())
                                }
                            }
                        }else{
                            //else go to welcome page
                            navHostController.navigate(Routes.Welcome())
                        }
                    }
                })
            }

            composable<Routes.Welcome> {
                welcomescreen{
                    navHostController.navigate(Routes.Login()){
                        popUpTo(0)
                    }
                }
            }

            composable<Routes.Login>{
                loginscreen(onlogin = {email ,password ->
                    coroutine.launch {
                        val trimmedEmail = email.trimStart().trimEnd()

                        if(isValidEmail(trimmedEmail)){
                            EventDispatcher.sendNetworkStatus(NetworkStates.INPROGRESS.name)

                            val result = loginViewModel.Login(trimmedEmail,password)
                            result.onSuccess {
                                SharedPreferencesUtil.saveCredentials(context,trimmedEmail,password)
                                navHostController.navigate(Routes.Home()) {
                                    popUpTo(0)
                                }
                                EventDispatcher.sendNetworkStatus(NetworkStates.DONE.name)
                            }

                            result.onFailure {throwable ->
                                when(throwable){
                                    is FirebaseAuthInvalidCredentialsException -> onshowsnackbar.invoke(INVALID_DETAILS_LOGIN)

                                    is FirebaseAuthInvalidUserException -> onshowsnackbar.invoke(INVALID_USER)

                                    else -> onshowsnackbar.invoke(INVALID)
                                }

                                EventDispatcher.sendNetworkStatus(NetworkStates.DONE.name)
                            }
                        }else{
                            onshowsnackbar.invoke(INVALID_EMAIL_FORMAT)
                        }
                    }

                },
                    onsignup = {navHostController.navigate(Routes.SignUp()){
                        popUpTo(0)
                    }})
            }

            composable<Routes.SignUp>{
                signupscreen(onclickback = {navHostController.navigateUp()},
                    onsubmit = {email,password ->
                        coroutine.launch {
                            val trimmedEmail = email.trimStart().trimEnd()

                            if (isValidEmail(trimmedEmail)) {
                                EventDispatcher.sendNetworkStatus(NetworkStates.INPROGRESS.name)

                                val result = signUpViewModel.SignUp(trimmedEmail, password)

                                result.onSuccess {
                                    SharedPreferencesUtil.saveCredentials(context, trimmedEmail, password)
                                    navHostController.navigate(Routes.Home()) {
                                        popUpTo(0)
                                    }
                                    EventDispatcher.sendNetworkStatus(NetworkStates.DONE.name)
                                }

                                result.onFailure { throwable ->
                                    when(throwable){
                                        is FirebaseAuthInvalidCredentialsException -> onshowsnackbar.invoke(INVALID_DETAILS_SIGN_UP)

                                        else -> onshowsnackbar.invoke(INVALID)
                                    }
                                    EventDispatcher.sendNetworkStatus(NetworkStates.DONE.name)
                                }
                            }else{
                                onshowsnackbar.invoke(INVALID_EMAIL_FORMAT)
                            }

                        }
                    })
            }

            composable<Routes.Home> {
                homescreen(onviewItem = {roomproduct ->
                    navHostController.navigate(Routes.ItemDetailsScreen("to be done"))
                }, onGoToMyBasket = {
                    navHostController.navigate(Routes.BasketScreen())
                }, onGoToMyOrderes = {
                    navHostController.navigate(Routes.OrdersScreen())
                }, name = "masiya")
            }

            composable<Routes.BasketScreen>{
                basketscreen(onclickback = {navHostController.navigateUp()},
                    onviewitem = {navHostController.navigate(Routes.ItemDetailsScreen())},
                    ongotoordercomplete = {navHostController.navigate(Routes.OrderCompleteScreen())},
                    ondeletefrombasket = {})
            }

            composable<Routes.OrdersScreen>{
                ordersscreen(onclickback = {
                    navHostController.navigateUp()
                }, onvieworder = {
                    navHostController.navigate(Routes.TrackOrderScreen())
                }, ondeleteorder = {

                })
            }

            composable<Routes.ItemDetailsScreen>{
                itemdetailsscreen(onaddtobasket = {}, ongoback = {navHostController.navigateUp()})
            }

            composable<Routes.OrderCompleteScreen>{
                ordercompletescreen(ongotohome = {navHostController.navigate(Routes.Home()){
                    popUpTo(0)
                } },
                    ongototrackorder ={navHostController.navigate(Routes.TrackOrderScreen())} )
            }

            composable<Routes.TrackOrderScreen>{
                trackorderscreen("order", ongotohome = {navHostController.navigate(Routes.Home()){
                    popUpTo(0)
                } })
            }
        }
    }
}

