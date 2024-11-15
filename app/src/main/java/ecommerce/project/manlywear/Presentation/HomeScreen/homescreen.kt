package ecommerce.project.manlywear.Presentation.HomeScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
fun homescreen(
    onviewItem: (productID: Int) -> Unit,
    onGoToMyOrderes: () -> Unit,
    onGoToMyBasket: () -> Unit,
    name: String,
    homeViewModel: HomeViewModel
) {
    ManlyWearTheme {
        var isVisible by remember { mutableStateOf(false) }

        // Trigger animations
        LaunchedEffect(Unit) {
            isVisible = true
        }

        // Screen fade-in animation
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1000))
        ) {
            Scaffold { paddingValues ->
                midSectionHomeScreen(
                    paddingValues = paddingValues,
                    name = name,
                    onviewItem = onviewItem,
                    onGoToMyBasket = onGoToMyBasket,
                    onGoToMyOrderes = onGoToMyOrderes,
                    homeViewModel = homeViewModel
                )
            }
        }
    }
}

@Composable
private fun midSectionHomeScreen(
    paddingValues: PaddingValues,
    name: String,
    onviewItem: (productID: Int) -> Unit,
    onGoToMyBasket: () -> Unit,
    onGoToMyOrderes: () -> Unit,
    homeViewModel: HomeViewModel
) {
    var search by remember { mutableStateOf("") }

    val shoppableList by homeViewModel.shoppableProducts.collectAsState()
    val filteredList = shoppableList.filter { product ->
        product.title.contains(search, true)
    }

    // Track animated visibility for items
    val transitionState = remember { MutableTransitionState(false).apply { targetState = true } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding() + 16.dp,
                start = 16.dp,
                end = 16.dp
            )
    ) {
        // Header with icon animations
        Row(
            modifier = Modifier
                .fillMaxWidth()
//                .animateEnterExit(
//                    enter = slideInHorizontally(tween(500)) + fadeIn(tween(500)),
//                    exit = slideOutHorizontally(tween(500)) + fadeOut(tween(500))
//                )
                    ,
            horizontalArrangement = Arrangement.End
        ) {
            ImageWithText(imageRes = R.drawable.orders) {
                onGoToMyOrderes.invoke()
            }
            Spacer(modifier = Modifier.width(8.dp))
            ImageWithText(imageRes = R.drawable.basket) {
                onGoToMyBasket.invoke()
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Animated greeting text
        AnimatedVisibility(
            visibleState = transitionState,
            enter = expandVertically(animationSpec = tween(600)) + fadeIn(tween(600)),
            exit = shrinkVertically(animationSpec = tween(600)) + fadeOut(tween(600))
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f),
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            letterSpacing = 0.sp
                        )
                    ) {
                        append("Hello $name ")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.tertiary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = 0.15.sp
                        )
                    ) {
                        append(stringResource(id = R.string.home_welcome_text))
                    }
                },
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Animated search bar
        EditText(
            modifier = Modifier
                .fillMaxWidth()
//                .animateEnterExit(
//                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
//                    exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
//                ),
                    ,
            ontype = { newtext ->
                search = newtext
            },
            ispassword = false,
            showTopHint = false,
            typedText = search,
            hint = "Search for items"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // LazyVerticalGrid with item animations
        LazyVerticalGrid(
            columns = GridCells.Adaptive(80.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            content = {
                items(filteredList) { shoppableProduct ->
                    AnimatedVisibility(
                        visible = transitionState.targetState,
                        enter = scaleIn() + fadeIn(animationSpec = tween(700)),
                        exit = scaleOut() + fadeOut(animationSpec = tween(700))
                    ) {
                        ShoppableItem(
                            imageResource = shoppableProduct.image,
                            itemName = shoppableProduct.title,
                            itemPrice = shoppableProduct.price
                        ) {
                            onviewItem.invoke(shoppableProduct.productId)
                        }
                    }
                }
            }
        )
    }
}
