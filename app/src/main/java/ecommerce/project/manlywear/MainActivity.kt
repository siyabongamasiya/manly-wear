package ecommerce.project.manlywear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ecommerce.project.manlywear.Presentation.BasketScreen.basketscreen
import ecommerce.project.manlywear.Presentation.HomeScreen.homescreen
import ecommerce.project.manlywear.Presentation.ItemDetailsScreen.itemdetailsscreen
import ecommerce.project.manlywear.Presentation.LoginScreen.loginscreen
import ecommerce.project.manlywear.Presentation.OrderCompleteScreen.ordercompletescreen
import ecommerce.project.manlywear.Presentation.OrdersScreen.ordersscreen
import ecommerce.project.manlywear.Presentation.Routes.Routes
import ecommerce.project.manlywear.Presentation.SignUpScreen.signupscreen
import ecommerce.project.manlywear.Presentation.SplashScreen.splashscreen
import ecommerce.project.manlywear.Presentation.TrackOrderScreen.trackorderscreen
import ecommerce.project.manlywear.Presentation.WelcomeScreen.welcomescreen
import ecommerce.project.manlywear.ui.theme.ManlyWearTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            ManlyWearTheme {
                App(navHostController = rememberNavController())
            }
        }
    }
}

@Composable
fun App(navHostController: NavHostController){
    NavHost(navController = navHostController, startDestination = Routes.Splash) {
        composable<Routes.Splash>{
            splashscreen{
                navHostController.navigate(Routes.Welcome()){
                    popUpTo(0)
                }
            }
        }

        composable<Routes.Welcome> {
            welcomescreen{
                navHostController.navigate(Routes.Login()){
                    popUpTo(0)
                }
            }
        }

        composable<Routes.Login>{
            loginscreen(onlogin = {navHostController.navigate(Routes.Home()){
                popUpTo(0)
            }},
                onsignup = {navHostController.navigate(Routes.SignUp()){
                    popUpTo(0)
                }})
        }

        composable<Routes.SignUp>{
            signupscreen(onclickback = {navHostController.navigateUp()},
                onsubmit = {navHostController.navigate(Routes.Home()){
                    popUpTo(0)
                } })
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