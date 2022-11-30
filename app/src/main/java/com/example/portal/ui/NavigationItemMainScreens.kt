package com.example.portal.ui

import com.example.portal.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Dishes : NavigationItem("dish", R.drawable.strava, "Dishes")
    object Products : NavigationItem("milkbottele", R.drawable.milkbottle, "Products")
    object Fridge : NavigationItem("fridge", R.drawable.fridge, "Fridge")
    object Basket : NavigationItem("basket", R.drawable.basket_food, "Basket")
    object Profile : NavigationItem("profile", R.drawable.profile_picture, "Profile")
}
