package com.example.portal

sealed class Routes(val route: String) {
    object AuthPage : Routes("Auth")
    object Login : Routes("Login")
    object Register : Routes("Register")
    object Main : Routes("Main")
    object Fridge : Routes("Fridge")
    object DietaryRestrictions : Routes("DietaryRestrictions")
}