package ecommerce.project.manlywear.Presentation.ItemDetailsScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ecommerce.project.manlywear.Domain.Model.retrofit.RetrofitProduct
import ecommerce.project.manlywear.Domain.Model.room.BasketProduct
import ecommerce.project.manlywear.Domain.Model.room.ShoppableProduct
import ecommerce.project.manlywear.Presentation.Components.CustomButton
import ecommerce.project.manlywear.Presentation.Components.OrderCounter
import ecommerce.project.manlywear.Presentation.Components.TopBar
import ecommerce.project.manlywear.R
import ecommerce.project.manlywear.ui.theme.ManlyWearTheme



@Composable
fun itemdetailsscreen(onaddtobasket: (basket : BasketProduct) -> Unit,
                      ongoback : () -> Unit,
                      productID : Int,
                      itemDetailsViewModel: ItemDetailsViewModel){

    val shoppableProduct = itemDetailsViewModel.shoppableproduct.collectAsState()


    LaunchedEffect(Unit) {
        itemDetailsViewModel.getShoppableProduct(productID)
    }
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
            Scaffold(topBar = {
                TopBar(modifier = Modifier, islight = false, onclickback = ongoback)
            }) {paddingValues ->
                midsectionItemsDetailsScreen(shoppableProduct = shoppableProduct.value,
                    paddingValues = paddingValues,
                    onaddtobasket = onaddtobasket)
            }
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun midsectionItemsDetailsScreen(shoppableProduct: ShoppableProduct?,
                                 paddingValues: PaddingValues,
                                 onaddtobasket : (basketProduct : BasketProduct) -> Unit){
    var orders by remember {
        mutableIntStateOf(1)
    }

    val basketProduct = BasketProduct(productId = shoppableProduct?.productId ?: 1,
        title = shoppableProduct?.title ?: "",
        price = shoppableProduct?.price ?: "",
        category = shoppableProduct?.category ?: "",
        description = shoppableProduct?.description ?: "",
        image = shoppableProduct?.image ?: "",
        no_of_ordered_items = orders)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary)
            .padding(top = paddingValues.calculateTopPadding() + 16.dp)
    ) {
        // Image section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f) // Allocate 30% of screen height
                .background(MaterialTheme.colorScheme.tertiary),
            contentAlignment = Alignment.Center
        ) {
            GlideImage(
                model = shoppableProduct?.image,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    //.size(80.dp)
                    .fillMaxSize()
                    .padding(bottom = 8.dp)
            )
        }

        // Content section
        Column(
            modifier = Modifier
                .weight(0.7f) // Allocate 70% of screen height
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title and order info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = shoppableProduct?.title ?: "loading..",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OrderCounter(
                        orders = orders,
                        onincrease = { orders++ },
                        ondecrease = {
                            if (orders > 1) orders--
                        }
                    )
                    Text(
                        text = "R${shoppableProduct?.price ?: " loading.."}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }

            // Description section with scrolling
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()), // Make this section scrollable
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.titleLarge,
                    text = stringResource(id = R.string.Description_text)
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodyLarge,
                    text = shoppableProduct?.description ?: "loading.."
                )
            }

            // Add to basket button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                CustomButton(
                    modifier = Modifier,
                    onclick = { onaddtobasket.invoke(basketProduct)},
                    text = stringResource(id = R.string.Add_to_basket)
                )
            }
        }
    }


}