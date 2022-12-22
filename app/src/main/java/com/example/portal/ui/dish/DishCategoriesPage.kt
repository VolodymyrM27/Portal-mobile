package com.example.portal.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
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
import com.example.portal.entities.dish.DishCategoryEntity
import com.example.portal.processError
import com.example.portal.ui.theme.BrightGreen
import com.example.portal.ui.theme.BrightYellow
import com.example.portal.viewmodels.DishViewModel

@Composable
fun DishCategoriesPage(
    dishViewModel: DishViewModel,
    activity: ComponentActivity,
    signOut: () -> Unit,
    showDishes: (categoryId: Int) -> Unit
) {
    val context = LocalContext.current
    val categories: MutableState<List<DishCategoryEntity>> = remember { mutableStateOf(listOf()) }
    val isLoading: MutableState<Boolean> = remember { mutableStateOf(false) }

    val token = "Bearer " + SessionManager.getToken(context)
    if (token == "Bearer ") {
        signOut()
        return
    }

    LaunchedEffect(Unit) {
        getDishCategories(
            dishViewModel = dishViewModel,
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
            DishCategoryItem(categoryEntity = category, onItemClick = { showDishes(category.Id) })
        }
    }

    if (isLoading.value)
    {
        CustomCircularProgressIndicator()
    }
}

@Composable
fun DishCategoryItem(categoryEntity: DishCategoryEntity, onItemClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .height(235.dp)
            .clickable { onItemClick() }
    ) {
        Card(
            backgroundColor = BrightYellow,
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
            backgroundColor = BrightYellow,
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


fun getDishCategories(
    dishViewModel: DishViewModel,
    activity: ComponentActivity,
    isLoading: MutableState<Boolean>,
    accessToken: String,
    categories: MutableState<List<DishCategoryEntity>>,
    signOut: () -> Unit
) {
    dishViewModel.getDishCategories(accessToken = accessToken)
    dishViewModel.dishCategoriesResult.observe(activity) {
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