package com.example.portal.ui.product

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
import com.example.portal.entities.ProductCategoryEntity
import com.example.portal.entities.ProductEntity
import com.example.portal.viewmodels.ProductViewModel

@Composable
fun ProductScreen(
    productViewModel: ProductViewModel,
    activity: ComponentActivity,
    onSignOut: () -> Unit
) {
    val navController = rememberNavController()
    val currentProduct = remember {
        mutableStateOf(
            ProductEntity(
                Id = -1,
                Name = "",
                Photo = "",
                Category = ProductCategoryEntity(Id = -1, Title = "", Photo = ""),
                Price = 0.0,
                Capacity = 0.0
            )
        )
    }

    NavHost(navController, startDestination = Routes.ProductCategories.route) {
        composable(route = Routes.ProductCategories.route) {
            ProductCategoriesPage(productViewModel = productViewModel,
                activity = activity,
                signOut = onSignOut,
                showProducts = {
                    navController.navigate("product-categories/$it/products")
                })
        }
        composable(Routes.Products.route, arguments = listOf(navArgument("category-id") {
            type = NavType.IntType
        })) {
            ProductsPage(
                productViewModel = productViewModel,
                activity = activity,
                signOut = onSignOut,
                productCategoryId = it.arguments?.getInt("category-id") ?: -1,
                showProductDescription = {
                    currentProduct.value = it
                    navController.navigate(Routes.ProductDescription.route)
                },
                goBack = { navController.popBackStack() }
            )
        }
        composable(Routes.ProductDescription.route) {
            ProductDescriptionPage(
                productEntity = currentProduct.value,
                goBack = { navController.popBackStack() }
            )
        }
    }
}