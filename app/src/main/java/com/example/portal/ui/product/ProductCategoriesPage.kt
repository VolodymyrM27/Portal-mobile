package com.example.portal.ui.product

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
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
import com.example.portal.auth.BaseResponse
import com.example.portal.auth.SessionManager
import com.example.portal.entities.ProductCategoryEntity
import com.example.portal.processError
import com.example.portal.ui.theme.BrightGreen
import com.example.portal.viewmodels.ProductViewModel

@Composable
fun ProductCategoriesPage(
    productViewModel: ProductViewModel,
    activity: ComponentActivity,
    signOut: () -> Unit,
    showProducts: (categoryId: Int) -> Unit
) {
    val context = LocalContext.current
    val categories: MutableState<List<ProductCategoryEntity>> = remember { mutableStateOf(listOf()) }
    val isLoading: MutableState<Boolean> = remember { mutableStateOf(false) }

    val token = "Bearer " + SessionManager.getToken(context)
    if (token == "Bearer ") {
        signOut()
        return
    }

    LaunchedEffect(Unit) {
        getProductCategories(
            productViewModel = productViewModel,
            activity = activity,
            isLoading = isLoading,
            categories = categories,
            accessToken = token,
            signOut = signOut
        )
    }

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
        items(categories.value) { category ->
            ProductCategoryItem(categoryEntity = category, onItemClick = { showProducts(category.Id) })
        }
    }

    if (isLoading.value)
    {
        CustomCircularProgressIndicator()
    }
}

@Composable
fun ProductCategoryItem(categoryEntity: ProductCategoryEntity, onItemClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .height(235.dp)
            .clickable { onItemClick() }
        //border = BorderStroke(2.dp, Color.Black)
    ) {
        Card(
            backgroundColor = BrightGreen,
            modifier = Modifier.height(235.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                Text(
                    text = categoryEntity.Title,
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
                painter = rememberAsyncImagePainter(categoryEntity.Photo),
                contentDescription = "photo",
                contentScale = ContentScale.Crop
            )
        }
    }
}


fun getProductCategories(
    productViewModel: ProductViewModel,
    activity: ComponentActivity,
    isLoading: MutableState<Boolean>,
    accessToken: String,
    categories: MutableState<List<ProductCategoryEntity>>,
    signOut: () -> Unit
) {
    productViewModel.getProductCategories(accessToken = accessToken)
    productViewModel.productCategoriesResult.observe(activity) {
        when (it) {
            is BaseResponse.Loading -> {
                isLoading.value = true
            }

            is BaseResponse.Success -> {
                isLoading.value = false
                categories.value = it.data?.toMutableList() ?: listOf()
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