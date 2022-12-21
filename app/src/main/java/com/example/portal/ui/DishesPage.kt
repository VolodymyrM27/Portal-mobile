package com.example.portal.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.portal.auth.BaseResponse
import com.example.portal.auth.SessionManager
import com.example.portal.dto.responses.dish.DishCategoryEntity
import com.example.portal.dto.responses.dish.DishEntity
import com.example.portal.processError
import com.example.portal.viewmodels.DishViewModel


@Composable
fun DishesPage(
    dishViewModel: DishViewModel,
    activity: ComponentActivity,
    signOut: () -> Unit,
    dishCategoryEntity: DishCategoryEntity
) {
    val context = LocalContext.current
    val dishes: MutableState<List<DishEntity>> = remember { mutableStateOf(listOf()) }
    val isLoading: MutableState<Boolean> = remember { mutableStateOf(false) }

    val token = "Bearer " + SessionManager.getToken(context)
    if (token == "Bearer ") {
        signOut()
        return
    }

    LaunchedEffect(Unit) {
        getDishesByCategory(
            dishViewModel = dishViewModel,
            activity = activity,
            isLoading = isLoading,
            dishes = dishes,
            accessToken = token,
            categoryId = dishCategoryEntity.Id,
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
        items(dishes.value) { dish ->
            DishItem(dishEntity = dish)
        }
    }
}

@Composable
fun DishItem(dishEntity: DishEntity) {
    Card(
        backgroundColor = Color.Red,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(200.dp),
        elevation = 8.dp,
        border = BorderStroke(2.dp, Color.Black)
    ) {
        Text(
            text = dishEntity.Name,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = Color(0xFFFFFFFF),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
        Image(
            painter = rememberAsyncImagePainter(dishEntity.Photo),
            contentDescription = "photo",
            contentScale = ContentScale.Crop
        )
    }
}


fun getDishesByCategory(
    dishViewModel: DishViewModel,
    activity: ComponentActivity,
    isLoading: MutableState<Boolean>,
    accessToken: String,
    categoryId: Int,
    dishes: MutableState<List<DishEntity>>,
    signOut: () -> Unit
) {
    dishViewModel.getDishByCategory(accessToken = accessToken, categoryId = categoryId)
    dishViewModel.dishByCategoryResult.observe(activity) {
        when (it) {
            is BaseResponse.Loading -> {
                isLoading.value = true
            }

            is BaseResponse.Success -> {
                isLoading.value = false
                dishes.value = it.data?.toMutableList() ?: listOf()
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