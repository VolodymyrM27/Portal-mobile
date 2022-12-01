package com.example.portal

sealed class Routes(var route: String, var icon: Int? = null, var title: String? =  null) {
    object AuthPage : Routes("Auth")
    object LoginPage : Routes("Login")
    object RegisterPage : Routes("Register")

    object MainPage : Routes("Main")

    object DietaryRestrictions : Routes("DietaryRestrictions")
    object MainScreen : Routes("MainScreen")
    object AuthScreen: Routes("AuthScreen")

    object Dishes : Routes("Dish", R.drawable.dish, "Dishes")
    object Products : Routes("MilkBottle", R.drawable.milk_bottle, "Products")
    object Fridge : Routes("Fridge", R.drawable.fridge, "Fridge")
    object Basket : Routes("BasketFood", R.drawable.basket_food, "Basket")
    object Profile : Routes("ProfilePicture", R.drawable.profile_picture, "Profile")
}
