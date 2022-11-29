package com.example.portal

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.portal.auth.BaseResponse
import com.example.portal.auth.SessionManager
import com.example.portal.google.AuthViewModel
import com.example.portal.responses.UserResponse
import com.example.portal.ui.MainPage
import com.example.portal.ui.LoginPage
import com.example.portal.ui.AuthPage
import com.example.portal.ui.SignUpPage
import com.example.portal.viewmodels.LoginViewModel
import com.example.portal.viewmodels.MainPageViewModel
import com.example.portal.viewmodels.SignUpViewModel


@Composable
fun PortalApp(
    activity: ComponentActivity,
    loginViewModel: LoginViewModel,
    mainPageViewModel: MainPageViewModel,
    signUpViewModel: SignUpViewModel
) {
    val navController = rememberNavController()
    val startDestination: MutableState<String> = remember { mutableStateOf(Routes.AuthPage.route) }
    val defaultLoginEmail: MutableState<String> = remember { mutableStateOf("") }
    val isLoading: MutableState<Boolean> = remember { mutableStateOf(false) }
    val currentUser: MutableState<UserResponse?> = remember { mutableStateOf(null) }


    val token = SessionManager.getToken(activity)
    if (!token.isNullOrBlank()) {
        goToMainPage(
            navController = navController,
            startDestination = startDestination,
            mainPageViewModel = mainPageViewModel,
            activity = activity,
            isLoading = isLoading,
            currentUser = currentUser
        )
    }

    NavHost(navController = navController, startDestination = startDestination.value) {
        val authViewModel = AuthViewModel()
        composable(Routes.AuthPage.route) {
            AuthPage(onLoginClicked = { navController.navigate(Routes.Login.route) },
                onRegisterClicked = { navController.navigate(Routes.Register.route) },
                authViewModel = authViewModel,
                onGoogleLoggedIn = { navController.navigate(Routes.Main.route) })
        }
        composable(Routes.Login.route) {
            LoginPage(onSignInClick = { email: String, password: String ->
                loginViewModel.loginUser(
                    email = email, pwd = password
                )
                handleLogin(loginViewModel = loginViewModel,
                    activity = activity,
                    isLoading = isLoading,
                    goToMainPage = {
                        goToMainPage(
                            navController = navController,
                            startDestination = startDestination,
                            mainPageViewModel = mainPageViewModel,
                            activity = activity,
                            isLoading = isLoading,
                            currentUser = currentUser
                        )
                    })
            }, isLoading = isLoading.value, defaultEmail = defaultLoginEmail.value)
        }
        composable(Routes.Register.route) {
            SignUpPage(onRegisterClicked = { email, name, password, repeatPassword ->
                handleSignUp(
                    signUpViewModel = signUpViewModel,
                    activity = activity,
                    isLoading = isLoading,
                    defaultLoginEmail = defaultLoginEmail,
                    navController = navController,
                    email = email,
                    name = name,
                    password = password,
                    repeatPassword = repeatPassword
                )
            }, isLoading = isLoading.value)
        }
        composable(Routes.Main.route) {
            MainPage(userResponse = currentUser.value, onLogOutClick = {
                logoutAndGoToAuthPage(
                    activity, navController, startDestination.value
                )
            }, isLoading = isLoading.value)
        }
    }
}

fun logoutAndGoToAuthPage(
    activity: ComponentActivity, navController: NavController, startDestination: String
) {
    SessionManager.clearData(activity)
    navController.popBackStack(startDestination, inclusive = true)
    navController.navigate(Routes.AuthPage.route)
}

fun goToMainPage(
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
        startDestination.value = Routes.Main.route
    } else {
        navController.popBackStack(startDestination.value, inclusive = true)
        navController.navigate(Routes.Main.route)
    }
}

fun handleLogin(
    loginViewModel: LoginViewModel,
    activity: ComponentActivity,
    isLoading: MutableState<Boolean>,
    goToMainPage: () -> Unit
) {
    loginViewModel.loginResult.observe(activity) { it ->
        when (it) {
            is BaseResponse.Loading -> {
                isLoading.value = true
            }
            is BaseResponse.Success -> {
                isLoading.value = false
                if (!it.data?.token.isNullOrEmpty()) {
                    it.data?.token?.let { it1 -> SessionManager.saveAuthToken(activity, it1) }
                    goToMainPage()
                }
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

fun handleSignUp(
    signUpViewModel: SignUpViewModel,
    activity: ComponentActivity,
    isLoading: MutableState<Boolean>,
    defaultLoginEmail: MutableState<String>,
    navController: NavController,
    email: String,
    name: String,
    password: String,
    repeatPassword: String
) {
    if (password != repeatPassword) {
        showToast(activity, "Passwords should be identical")

    } else {
        navController.navigate(Routes.Register.route)
    }
    signUpViewModel.signUpUser(email = email, pwd = password, name = name)
    signUpViewModel.signUpResult.observe(activity) { it ->
        when (it) {
            is BaseResponse.Loading -> {
                isLoading.value = true
            }
            is BaseResponse.Success -> {
                isLoading.value = false
                showToast(activity, it.data?.message ?: "success")
                defaultLoginEmail.value = email
                navController.navigate(Routes.Login.route)
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

fun processError(context: Context, msg: String?) {
    showToast(context, "Error:$msg")
}

fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}



