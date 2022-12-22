package com.example.portal.ui.product

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.portal.CustomCircularProgressIndicator
import com.example.portal.GreenHeader
import com.example.portal.YellowHeader
import com.example.portal.auth.BaseResponse
import com.example.portal.auth.SessionManager
import com.example.portal.entities.ProductEntity
import com.example.portal.processError
import com.example.portal.ui.theme.BackgroundGrey
import com.example.portal.ui.theme.BrightGreen
import com.example.portal.ui.theme.LightGreen
import com.example.portal.viewmodels.ProductViewModel

@Composable
fun ProductsPage(
    productViewModel: ProductViewModel,
    activity: ComponentActivity,
    signOut: () -> Unit,
    productCategoryId: Int,
    showProductDescription: (ProductEntity) -> Unit,
    goBack: () -> Unit
) {
    val context = LocalContext.current
    val products: MutableState<List<ProductEntity>> = remember { mutableStateOf(listOf()) }
    val isLoading: MutableState<Boolean> = remember { mutableStateOf(false) }

    val token = "Bearer " + SessionManager.getToken(context)
    if (token == "Bearer ") {
        signOut()
        return
    }

    LaunchedEffect(Unit) {
        getProductsByCategory(
            productViewModel = productViewModel,
            activity = activity,
            isLoading = isLoading,
            products = products,
            accessToken = token,
            categoryId = productCategoryId,
            signOut = signOut
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundGrey)
    ) {
        GreenHeader(
            text = if (products.value.isEmpty()) "" else products.value[0].Category.Title,
            hasBackButton = true,
            onBackButtonClick = goBack
        )

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize(),

            columns = GridCells.Adaptive(128.dp),

            // content padding
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp
            ),


            )
        {
            items(products.value) { product ->
                ProductItem(productEntity = product, showProductDescription = { showProductDescription(product) })
            }
        }
    }

    if (isLoading.value) {
        CustomCircularProgressIndicator()
    }
}

@Composable
fun ProductItem(productEntity: ProductEntity, showProductDescription: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .height(255.dp)
            .clickable { showProductDescription() }
        //border = BorderStroke(2.dp, Color.Black)
    ) {
        Card(
            backgroundColor = LightGreen,
            modifier = Modifier
                .height(255.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                Text(
                    text = productEntity.Price.toString() + " грн",
                    style = MaterialTheme.typography.body2,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Card(
            backgroundColor = BrightGreen,
            modifier = Modifier.height(235.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                Text(
                    text = productEntity.Name,
                    style = MaterialTheme.typography.body2,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Card(
            backgroundColor = BrightGreen,
            modifier = Modifier.height(210.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(productEntity.Photo),
                contentDescription = "photo",
                contentScale = ContentScale.Crop
            )
        }
    }
}


fun getProductsByCategory(
    productViewModel: ProductViewModel,
    activity: ComponentActivity,
    isLoading: MutableState<Boolean>,
    accessToken: String,
    categoryId: Int,
    products: MutableState<List<ProductEntity>>,
    signOut: () -> Unit
) {
    productViewModel.getProductsByCategory(accessToken = accessToken, categoryId = categoryId)
    productViewModel.productByCategoryResult.observe(activity) {
        when (it) {
            is BaseResponse.Loading -> {
                isLoading.value = true
            }

            is BaseResponse.Success -> {
                isLoading.value = false
                products.value = it.data?.toMutableList() ?: listOf()
            }

            is BaseResponse.Unauthorized -> {
                isLoading.value = false
                //processError(activity, it.msg)
                signOut()
                return@observe
            }

            is BaseResponse.Error -> {
                isLoading.value = false
                processError(activity, it.msg)
            }

            else -> {
                isLoading.value = false
            }
        }
    }
}
