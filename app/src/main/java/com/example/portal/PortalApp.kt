package com.example.portal

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.portal.auth.SessionManager
import com.example.portal.ui.*
import com.example.portal.viewmodels.AuthViewModel


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun PortalApp(
    activity: ComponentActivity,
    authViewModel: AuthViewModel
) {
    val navController = rememberNavController()
    val startDestination: MutableState<String> =
        remember { mutableStateOf(Routes.AuthScreen.route) }

    val token = SessionManager.getToken(activity)
    if (!token.isNullOrBlank()) {
        startDestination.value = Routes.MainScreen.route
    }

//    val isLoading: MutableState<Boolean> = remember { mutableStateOf(false) }
//    val currentUser: MutableState<UserResponse?> = remember { mutableStateOf(null) }

    NavHost(navController = navController, startDestination = startDestination.value) {
        composable(Routes.AuthScreen.route) {
            AuthScreen(activity = activity, authViewModel = authViewModel, goToMainScreen = {
                navController.popBackStack(Routes.AuthScreen.route, inclusive = true)
                navController.navigate(Routes.MainScreen.route)
            })
        }
        composable(Routes.MainScreen.route) {
            MainScreen(activity = activity, goToAuthScreen = {
                navController.popBackStack(Routes.MainScreen.route, inclusive = true)
                navController.navigate(Routes.AuthScreen.route)
            })
        }

//        composable(Routes.MainPage.route) {
//            MainPage(userResponse = currentUser.value, onLogOutClick = {
//                logoutAndGoToAuthPage(
//                    activity, navController, startDestination.value
//                )
//            }, isLoading = isLoading.value, goToFridge = {
//                navController.popBackStack(Routes.MainPage.route, inclusive = true)
//                navController.navigate(Routes.Fridge.route)
//            })
//        }
//        composable(Routes.Fridge.route) {
//            Fridge()
//        }
//        composable(Routes.DietaryRestrictions.route) {
//            val restrictions = remember {
//                mutableListOf(
//                    DietaryRestrictionEntity(
//                        Id = 1,
//                        Title = "Вегетеріанство",
//                        Image = R.drawable.ic_launcher_background
//                    ),
//                    DietaryRestrictionEntity(
//                        Id = 2,
//                        Title = "Халяль",
//                        Image = R.drawable.ic_launcher_background
//                    )
//                )
//            }
//            DietaryRestrictions(
//                restrictions = restrictions,
//                deleteItem = { id ->
//                    restrictions.removeIf { x ->
//                        x.Id == id
//                    }
//                }
//            )
//        }
    }
}

//fun logoutAndGoToAuthPage(
//    activity: ComponentActivity, navController: NavController, startDestination: String
//) {
//    SessionManager.clearData(activity)
//    navController.popBackStack(startDestination, inclusive = true)
//    navController.navigate(Routes.AuthPage.route)
//}

/*fun goToMainPage(
    navController: NavController,
    activity: ComponentActivity,
    mainPageViewModel: MainPageViewModel,
    currentUser: MutableState<UserResponse?>,
    startDestination: MutableState<String>,
    isLoading: MutableState<Boolean>
) {
    mainPageViewModel.getCurrentUser(("Bearer " + SessionManager.getToken(activity)))
    mainPageViewModel.currentUserResult.observe(activity) {
        when (it) {
            is BaseResponse.Loading -> {
                isLoading.value = true
            }
            is BaseResponse.Success -> {
                isLoading.value = false
                currentUser.value = it.data
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

    if (navController.backQueue.isEmpty()) {
        startDestination.value = Routes.MainPage.route
    } else {
        navController.popBackStack(startDestination.value, inclusive = true)
        navController.navigate(Routes.MainPage.route)
    }
}*/
