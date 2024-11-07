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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ecommerce.project.manlywear.Domain.Model.retrofit.RetrofitProduct
import ecommerce.project.manlywear.Presentation.Components.CustomButton
import ecommerce.project.manlywear.Presentation.Components.OrderCounter
import ecommerce.project.manlywear.Presentation.Components.TopBar
import ecommerce.project.manlywear.R
import ecommerce.project.manlywear.ui.theme.ManlyWearTheme



@Composable
fun itemdetailsscreen(onaddtobasket: () -> Unit,ongoback : () -> Unit){
    val p = RetrofitProduct(1,"watch","340","mens","some watch djdnfjnfm fkjfnfkkfj jjnnj nfnnfjjfigjjjgn g gkkkkk","jjj")
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
                midsectionItemsDetailsScreen(retrofitProduct = p,
                    paddingValues = paddingValues,
                    onaddtobasket = onaddtobasket)
            }
        }

    }
}

@Composable
fun midsectionItemsDetailsScreen(retrofitProduct: RetrofitProduct,
                                 paddingValues: PaddingValues,
                                 onaddtobasket : () -> Unit){
    var orders by remember {
        mutableIntStateOf(0)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.tertiary)
        .padding(top = paddingValues.calculateTopPadding() + 16.dp)) {

        Box (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.tertiary)
                .weight(0.4f),
            contentAlignment = Alignment.Center){
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(50.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "item image")
        }

        Column(modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Column(modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = retrofitProduct.title,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.tertiary)
                Row (modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    OrderCounter(orders = orders, onincrease = {
                        orders++
                    }, ondecrease = {
                        if (orders > 0) {
                            orders--
                        }
                    })

                    Text(text = "R${retrofitProduct.price}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.tertiary)

                }
                
            }

            Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally){

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.titleLarge,
                    text = stringResource(id = R.string.Description_text))
                Text(modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodyLarge,
                    text = retrofitProduct.description)

            }

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End){
                CustomButton(modifier = Modifier,
                    onclick = { },
                    text = stringResource(id = R.string.Add_to_basket))
            }

        }



    }


}