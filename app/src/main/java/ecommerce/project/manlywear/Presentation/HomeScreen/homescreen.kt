package ecommerce.project.manlywear.Presentation.HomeScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ecommerce.project.manlywear.Domain.Model.room.BasketProduct
import ecommerce.project.manlywear.Domain.Model.room.ShoppableProduct
import ecommerce.project.manlywear.Domain.UseCases.LocallyFetchShoppableProducts
import ecommerce.project.manlywear.Presentation.Components.EditText
import ecommerce.project.manlywear.Presentation.Components.ImageWithText
import ecommerce.project.manlywear.Presentation.Components.ShoppableItem
import ecommerce.project.manlywear.R
import ecommerce.project.manlywear.ui.theme.ManlyWearTheme


@Composable
fun homescreen(onviewItem : (productID : Int) -> Unit,
               onGoToMyOrderes : () -> Unit,
               onGoToMyBasket : () -> Unit,
               name : String,
               homeViewModel: HomeViewModel){

    ManlyWearTheme {
        var isvisible by remember {
            mutableStateOf(false)
        }

        LaunchedEffect(Unit) {
            isvisible = true
        }

        AnimatedVisibility(visible = isvisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 2000)),
            exit = fadeOut(animationSpec = tween(durationMillis = 2000))
        ) {
            Scaffold {paddingValues ->
                midSectionHomeScreen(paddingValues = paddingValues,
                    name = name,
                    onviewItem,
                    onGoToMyBasket,
                    onGoToMyOrderes,
                    homeViewModel)
            }
        }

    }
}

@Composable
private fun midSectionHomeScreen(paddingValues: PaddingValues,
                                 name : String,
                                 onviewItem: (productID : Int) -> Unit,
                                 onGoToMyBasket: () -> Unit,
                                 onGoToMyOrderes: () -> Unit,
                                 homeViewModel: HomeViewModel) {
    var search by remember {
        mutableStateOf("")
    }

    val shoppablelist by homeViewModel.shoppableProducts.collectAsState()
    val filteredlist = shoppablelist.filter {product ->
        product.title.contains(search,true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding() + 16.dp, start = 16.dp, end = 16.dp)
    ) {
        // Row with two icons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            ImageWithText(imageRes = R.drawable.orders){
                onGoToMyOrderes.invoke()
            }
            ImageWithText(imageRes = R.drawable.basket){
                onGoToMyBasket.invoke()
            }

        }

        Spacer(modifier = Modifier.height(8.dp))

        // Annotated text
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f),
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    letterSpacing = 0.sp)) {
                    append("Hello $name ")
                }

                withStyle(style = SpanStyle(
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.15.sp)) {
                    append(stringResource(id = R.string.home_welcome_text))
                }
            },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        //edit text
        EditText(modifier = Modifier.fillMaxWidth(),
            ontype = {newtext ->
                search = newtext
            },
            ispassword = false,
            showTopHint = false,
            typedText = search,
            hint = "Search for items")

        // Grid layout with two columns
        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(80.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            content = {

                items(filteredlist){shoppableproduct ->
                    ShoppableItem(imageResource = shoppableproduct.image,
                        itemName = shoppableproduct.title,
                        itemPrice = shoppableproduct.price){

                        onviewItem.invoke(shoppableproduct.productId)

                    }
                }

            }
        )
    }
}