package ecommerce.project.manlywear.Presentation.Components

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ecommerce.project.manlywear.Presentation.BasketScreen.BasketViewModel
import ecommerce.project.manlywear.R
import ecommerce.project.manlywear.ui.theme.BACKGROUND_OF_SUCCESS_CIRCLE
import ecommerce.project.manlywear.ui.theme.SUCCESS_CIRCLE
import kotlinx.coroutines.launch
import java.net.URL

@Composable
fun EditText(modifier: Modifier,
             ontype :(newtext : String) -> Unit,
             ispassword : Boolean,
             typedText : String,
             showTopHint : Boolean = true,
             readonly : Boolean = false,
             isnumber : Boolean = false,
             hint : String){

    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        val keyboardOptions : KeyboardOptions = if (isnumber) {
            KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone
            )
        }else if (ispassword){
            KeyboardOptions.Default.copy(
                // Setting the keyboard type for password input
                keyboardType = KeyboardType.Password
            )
        }else{
            KeyboardOptions.Default.copy(
                // Setting the keyboard type for password input
                keyboardType = KeyboardType.Text
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = if (showTopHint) hint else "",
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.titleLarge)

        if (ispassword) {
            OutlinedTextField(
                readOnly = readonly,
                modifier = modifier
                    .alpha(0.6f),
                visualTransformation = if (passwordVisible) {
                    // Shows the text as plain text
                    androidx.compose.ui.text.input.VisualTransformation.None
                } else {
                    // Masks the text with asterisks or dots
                    androidx.compose.ui.text.input.PasswordVisualTransformation()
                },
                trailingIcon = {
                    val image = if (passwordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = image,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                    cursorColor = MaterialTheme.colorScheme.onSecondary,
                    focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSecondary
                ),

                keyboardOptions = keyboardOptions,
                value = typedText,
                onValueChange = { newtext ->
                    if(newtext.isNotEmpty()) {
                        if (isnumber) {
                            val text = newtext.filter { it.isDigit() }
                            ontype.invoke(text)
                        } else {
                            ontype.invoke(newtext)
                        }
                    }
                },
                placeholder = {
                    Text(
                        modifier = Modifier.alpha(0.6f),
                        text = hint,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            )
        }else{
            OutlinedTextField(
                readOnly = readonly,
                modifier = modifier
                    .alpha(0.6f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                    cursorColor = MaterialTheme.colorScheme.onSecondary,
                    focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSecondary
                ),
                value = typedText,
                onValueChange = { newtext ->
                    ontype.invoke(newtext)
                },
                keyboardOptions = keyboardOptions,
                placeholder = {
                    Text(
                        modifier = Modifier.alpha(0.6f),
                        text = hint,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            )
        }
    }

}

@Composable
fun CustomButton(modifier: Modifier,onclick : () -> Unit,text : String){
    ElevatedButton(
        modifier = modifier,
        onClick = {
            onclick.invoke()
        },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        )) {
        Box(modifier = Modifier
            //.fillMaxWidth()
            .padding(16.dp),
            contentAlignment = Alignment.Center){

            Text(modifier = Modifier,
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onTertiary)

        }

    }
}

@Composable
fun TopBar(modifier: Modifier,
           onclickback : () -> Unit,
           islight : Boolean = true,
           title : String = ""){
    Row (modifier = modifier
        .fillMaxWidth()
        .background(if (islight) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary)
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween){

        Icon(
            modifier = Modifier.clickable {
                onclickback.invoke()
            },
            tint = if (islight) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary,
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "back arrow")

        Text(text = title,
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.size(30.dp))

    }
}

@Composable
fun ImageWithText(
    imageRes: Int,
    onclick: () -> Unit
) {
    Box(contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "image",
            modifier = Modifier
                .size(100.dp)
                .clickable {
                    onclick.invoke()
                }
        )
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ShoppableItem(
    imageResource: String,
    itemName: String,
    itemPrice: String,
    onclick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(16.dp)
            .clickable {
                onclick.invoke()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            model = imageResource,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 8.dp)
        )


        Text(
            text = itemName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSecondary
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "R${itemPrice}",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSecondary)
    }
}

@Composable
fun OrderItem(modifier: Modifier,
               orderID : String,
               orderTotal : Double,
               ondeleteOrder : () -> Unit,
               onclick: () -> Unit){

    Row (modifier = modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(MaterialTheme.colorScheme.secondary)
        .padding(16.dp)
        .clickable {
            onclick.invoke()
        },
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically){

        Spacer(modifier = Modifier.size(20.dp))

        Text(text = orderID,
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.titleLarge)

        Text(text = "R${orderTotal}",
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.titleMedium)
        Icon(modifier = Modifier
            .size(24.dp)
            .clickable {
                ondeleteOrder.invoke()
            },
            tint = MaterialTheme.colorScheme.onSecondary,
            imageVector = Icons.Default.Delete,
            contentDescription = "delete product from basket")
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BasketItem(modifier: Modifier,
               itemname : String,
               itempic : String,
               numberOrdered : Int,
               itemPrice : String,
               ondeletefrombasket : () -> Unit,
               onclick: () -> Unit){

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onclick.invoke() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Image section
        GlideImage(
            model = itempic,
            contentScale = ContentScale.Crop,
            contentDescription = "item pic",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )

        // Text section
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = itemname,
                color = MaterialTheme.colorScheme.onSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${numberOrdered} items ordered",
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        // Price Text
        Text(
            text = "R${itemPrice}",
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(end = 8.dp)
        )

        // Delete Icon
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable { ondeletefrombasket.invoke() },
            tint = MaterialTheme.colorScheme.onSecondary,
            imageVector = Icons.Default.Delete,
            contentDescription = "delete product from basket"
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheetWithCloseButton(
    sheetState: SheetState,
    modifier: Modifier,
    ongotoordercomplete: () -> Unit,
    onclose: () -> Unit,
    basketViewModel: BasketViewModel
) {
    var showCardDetails by remember { mutableStateOf(false) }

    if (sheetState.isVisible) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { onclose.invoke() },
            scrimColor = Color.Black.copy(alpha = 0.2f),
            modifier = modifier.navigationBarsPadding()
        ) {
            if (showCardDetails) {
                CardDetails(
                    onclosecarddetails = { showCardDetails = false },
                    ongotoordercomplete = ongotoordercomplete,
                    onclose = onclose,
                    basketViewModel = basketViewModel
                )
            } else {
                DeliveryDetails(
                    ongotoordercomplete = ongotoordercomplete,
                    onclose = onclose,
                    basketViewModel = basketViewModel
                ) {
                    showCardDetails = true
                }
            }
        }
    }
}

@Composable
private fun CardDetails(ongotoordercomplete: () -> Unit,
                        onclosecarddetails : () -> Unit,
                        onclose: () -> Unit,
                        basketViewModel: BasketViewModel){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val orderDetails by basketViewModel.orderDetails.collectAsState()

        var cardholder = orderDetails.cardName
        var cardnumber = orderDetails.cardNumber
        var date = orderDetails.date
        var ccv = orderDetails.ccv

        // Close button with cross icon
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically) {

            Icon(modifier = Modifier.clickable {
                onclosecarddetails.invoke()
            },
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back arrow")
            IconButton(
                onClick = { onclose.invoke() },
                modifier = Modifier
                    .padding(16.dp)
                    .size(36.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
            Spacer(modifier = Modifier.size(30.dp))
        }

        Column (modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            EditText(
                modifier = Modifier.fillMaxWidth(),
                ontype = { newtext ->
                    basketViewModel.updateCardDetails(newtext,null,null,null)
                },
                ispassword = false,
                typedText = cardholder,
                hint = "Card Holder Name"
            )

            EditText(
                modifier = Modifier.fillMaxWidth(),
                ontype = { newnumber ->
                    basketViewModel.updateCardDetails(null,newnumber,null,null)
                },
                ispassword = true,
                typedText = "$cardnumber",
                isnumber = true,
                hint = "Card Number"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                EditText(
                    modifier = Modifier.size(100.dp),
                    ontype = {},
                    ispassword = false,
                    typedText = date,
                    readonly = true,
                    hint = "Date"
                )

                EditText(
                    modifier = Modifier.size(100.dp),
                    ontype = { newnumber ->
                        basketViewModel.updateCardDetails(null,null,null,newnumber)
                    },
                    ispassword = true,
                    typedText = "$ccv",
                    isnumber = true,
                    hint = "CCV"
                )

            }

            CustomButton(
                modifier = Modifier.fillMaxWidth(),
                onclick = { ongotoordercomplete.invoke() },
                text = stringResource(id = R.string.basket_complete_order)
            )
        }
    }
}

@Composable
private fun DeliveryDetails(ongotoordercomplete: () -> Unit,onclose: () -> Unit,
                            basketViewModel: BasketViewModel,
                            onshowcarddetails : () -> Unit){
    val orderDetails by basketViewModel.orderDetails.collectAsState()

    var deliveryAddres = orderDetails.deliveryAddress
    var phonenumber = orderDetails.phoneNumber

    // Close button with cross icon
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically) {

        Spacer(modifier = Modifier.size(30.dp))
        IconButton(
            onClick = { onclose.invoke() },
            modifier = Modifier
                .padding(16.dp)
                .size(36.dp)
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
        Spacer(modifier = Modifier.size(30.dp))
    }

    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            EditText(modifier = Modifier.fillMaxWidth(),
                ontype = {newtext ->
                    basketViewModel.updateDeliveryDetails(newtext,null)
                },
                ispassword = false,
                typedText = deliveryAddres,
                hint = "Delivery Address")

            EditText(modifier = Modifier.fillMaxWidth(),
                ontype = {newnumber ->
                    basketViewModel.updateDeliveryDetails(null,newnumber)
                },
                ispassword = false,
                typedText = "$phonenumber",
                isnumber = true,
                hint = "Phone Number")

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically) {

                CustomButton(modifier = Modifier.weight(1f), onclick = { ongotoordercomplete.invoke() }, text = stringResource(
                    id = R.string.basket_pay_on_delivery
                ))
                CustomButton(modifier = Modifier.weight(1f), onclick = { onshowcarddetails.invoke() }, text = stringResource(
                    id = R.string.basket_pay_with_card
                ))

            }
        }

    }


}

@Composable
fun OrderCounter(orders : Int,
                 onincrease : () -> Unit,
                 ondecrease : () -> Unit){
    
    Row(
        modifier = Modifier.background(MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Box(modifier = Modifier
            .size(32.dp)
            .clickable {
                ondecrease.invoke()
            }
            .border(
                BorderStroke(2.dp, MaterialTheme.colorScheme.tertiary), // Border width and color
                shape = CircleShape,
            ),
            contentAlignment = Alignment.Center){
            Text(text = "-", color = MaterialTheme.colorScheme.tertiary,)
        }

        Text(text = "${orders}",
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.titleLarge)

        Box(modifier = Modifier
            .size(32.dp)
            .clickable {
                onincrease.invoke()
            }
            .background(MaterialTheme.colorScheme.tertiary, shape = CircleShape),
            contentAlignment = Alignment.Center){
            Text(text = "+", color = MaterialTheme.colorScheme.primary)
        }
    }
    
}

@Composable
fun Congratulator(modifier: Modifier){
    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {

        GreenCheck(modifier = Modifier.padding(bottom = 32.dp), size = 200)
        Text(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.Congratulations),
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.headlineLarge)
        Text(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.Congrats_description),
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.bodyLarge)

    }
}

@Composable
private fun GreenCheck(modifier: Modifier,size : Int){
    Box (modifier = modifier
        .size(size.dp)
        .background(shape = CircleShape, color = BACKGROUND_OF_SUCCESS_CIRCLE)
        .border(width = 2.dp, color = SUCCESS_CIRCLE, shape = CircleShape)
        .padding(16.dp),
        contentAlignment = Alignment.Center) {

        Icon(
            modifier = Modifier
                .size(size.dp),
            imageVector = Icons.Default.CheckCircle,
            tint = SUCCESS_CIRCLE,
            contentDescription = "green check")

    }
}

@Composable
fun Status(modifier: Modifier,text: String,imageRes: Int?){
    Row (modifier = modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.primary)
        .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically){

        Row (modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically){

            if (imageRes != null) {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = imageRes),
                    contentDescription = "status image"
                )
            }else{
                Spacer(modifier = Modifier.size(24.dp))
            }

            Text(text = text,
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.titleLarge)

        }

        Icon(
            modifier = Modifier
                .size(24.dp),
            imageVector = Icons.Default.CheckCircle,
            tint = SUCCESS_CIRCLE,
            contentDescription = "green check")

    }
}

@Composable
fun LevelStatus(modifier: Modifier,status : Int){
    when(status){
        1 -> {
            Column (modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally){

                Status(modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.order_taken),
                    imageRes = R.drawable.writingonfile)


            }
        }

        2 -> {
            Column (modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally){

                Status(modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.order_taken),
                    imageRes = R.drawable.writingonfile)

                Status(modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.order_is_being_prepared),
                    imageRes = R.drawable.clipboard)


            }
        }

        3 -> {
            Column (modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally){

                Status(modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.order_taken),
                    imageRes = R.drawable.writingonfile)

                Status(modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.order_is_being_prepared),
                    imageRes = R.drawable.clipboard)

                Status(modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.order_is_being_delivered),
                    imageRes = R.drawable.deliveryman)


            }
        }

        4 -> {
            Column (modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally){

                Status(modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.order_taken),
                    imageRes = R.drawable.writingonfile)

                Status(modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.order_is_being_prepared),
                    imageRes = R.drawable.clipboard)

                Status(modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.order_is_being_delivered),
                    imageRes = R.drawable.deliveryman)

                Status(modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.order_received),
                    imageRes = null)

            }
        }
    }
}


@Composable
fun LifecycleAwareComposable(
    lifecycleOwner: LifecycleOwner ,
    ongotosplash : () -> Unit
) {
    // Observe lifecycle changes
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if(FirebaseAuth.getInstance().currentUser == null){
                    ongotosplash.invoke()
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}






@Preview
@Composable
fun Test(){
    LevelStatus(modifier = Modifier, status = 4)
}