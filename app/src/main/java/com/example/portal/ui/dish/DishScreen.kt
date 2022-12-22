package com.example.portal.ui

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.portal.Routes
import com.example.portal.entities.dish.DishCategoryEntity
import com.example.portal.entities.dish.DishEntity
import com.example.portal.viewmodels.DishViewModel

@Composable
fun DishScreen(
    dishViewModel: DishViewModel,
    activity: ComponentActivity,
    onSignOut: () -> Unit
) {
    val navController = rememberNavController()
    val currentDish = remember {
        mutableStateOf(
            DishEntity(
                Id = -1,
                Name = "",
                Photo = "",
                Category = DishCategoryEntity(Id = -1, Title = "", Photo = ""),
                Caloricity = 0f,
                DishProducts = listOf(),
                Instructions = listOf()
            )
        )
    }

    NavHost(navController, startDestination = Routes.DishCategories.route) {
        composable(route = Routes.DishCategories.route) {
            DishCategoriesPage(dishViewModel = dishViewModel,
                activity,
                signOut = onSignOut,
                showDishes = {
                    navController.navigate("dish-categories/$it/dishes")
                })
        }
        composable(Routes.Dishes.route, arguments = listOf(navArgument("category-id") {
            type = NavType.IntType
        })) {
            DishesPage(
                dishViewModel = dishViewModel,
                activity,
                signOut = onSignOut,
                dishCategoryId = it.arguments?.getInt("category-id") ?: -1,
                showDishDescription = {
                    currentDish.value = it
                    navController.navigate(Routes.DishDescription.route)
                },
                goBack = { navController.popBackStack() }
            )
        }
        composable(Routes.DishDescription.route) {
            DishDescriptionPage(
                dishEntity = currentDish.value,
                goBack = { navController.popBackStack() }
            )
        }
    }
}