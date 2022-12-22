package com.example.portal

sealed class Routes(var route: String, var icon: Int? = null, var title: String? =  null) {
    object AuthPage : Routes("Auth")
    object LoginPage : Routes("Login")
    object RegisterPage : Routes("Register")

    object MainPage : Routes("Main")

    object DietaryRestrictions : Routes("DietaryRestrictions")
    object MainScreen : Routes("MainScreen")
    object AuthScreen: Routes("AuthScreen")

    object DishCategories : Routes("dish-categories")
    object Dishes : Routes("dish-categories/{category-id}/dishes")
    object DishDescription : Routes("DishDescription")

    object ProductCategories : Routes("product-categories")
    object Products : Routes("product-categories/{category-id}/products")
    object ProductDescription : Routes("ProductDescription")

    object DishScreen : Routes("dish-screen", R.drawable.dish_svgrepo_com, "DishScreen")
    object ProductScreen : Routes("ProductScreen", R.drawable.milk_products_svgrepo_com, "ProductScreen")
    object Fridge : Routes("Fridge", R.drawable.fridge_svgrepo_com, "Fridge")
    object Basket : Routes("BasketFood", R.drawable.basket_svgrepo_com, "Basket")
    object Profile : Routes("ProfilePicture", R.drawable.user_svgrepo_com, "Profile")
}
