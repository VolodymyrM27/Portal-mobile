package com.example.portal.ui

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.portal.*
import com.example.portal.auth.BaseResponse
import com.example.portal.auth.SessionManager
import com.example.portal.dto.SignInDTO
import com.example.portal.dto.SignUpDTO
import com.example.portal.viewmodels.AuthViewModel

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AuthScreen(authViewModel: AuthViewModel, activity: ComponentActivity, goToMainScreen: () -> Unit) {
    val navController = rememberNavController()
    val defaultLoginEmail: MutableState<String> = remember { mutableStateOf("") }
    val isLoading: MutableState<Boolean> = remember { mutableStateOf(false) }

    AuthNavigation(navController = navController,
        isLoading = isLoading,
        defaultLoginEmail = defaultLoginEmail,
        onSignIn = { signInDTO ->
            handleSignIn(
                authViewModel = authViewModel,
                activity = activity,
                isLoading = isLoading,
                goToMainScreen = goToMainScreen,
                signInDTO = signInDTO
            )
        },
        onSignUp = { signUpDTO ->
            handleSignUp(
                authViewModel = authViewModel,
                activity = activity,
                isLoading = isLoading,
                goToSignInPage = { email ->
                    defaultLoginEmail.value = email
                    goToSignInPage(navController)
                },
                signUpDTO = signUpDTO
            )
        },
        onGoogleSignIn = {})
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AuthNavigation(
    navController: NavHostController,
    isLoading: MutableState<Boolean>,
    defaultLoginEmail: MutableState<String>,
    onSignIn: (signInDTO: SignInDTO) -> Unit,
    onSignUp: (signUpDTO: SignUpDTO) -> Unit,
    onGoogleSignIn: () -> Unit
) {
    NavHost(navController = navController, startDestination = Routes.AuthPage.route) {
        composable(Routes.AuthPage.route) {
            AuthPage(
                goToSignInClicked = { goToSignInPage(navController) },
                goToSignUpClicked = { goToSignUpPage(navController) },
                signInWithGoogle = onGoogleSignIn
            )
        }
        composable(Routes.LoginPage.route) {
            LoginPage(
                onSignInClick = onSignIn,
                isLoading = isLoading.value,
                defaultEmail = defaultLoginEmail.value
            )
        }
        composable(Routes.RegisterPage.route) {
            SignUpPage(onSignUpClick = onSignUp, isLoading = isLoading.value)
        }
    }
}

fun goToSignInPage(navController: NavHostController) {
    navController.navigate(Routes.LoginPage.route)
}

fun goToSignUpPage(navController: NavHostController) {
    navController.navigate(Routes.RegisterPage.route)
}

fun handleSignIn(
    authViewModel: AuthViewModel,
    activity: ComponentActivity,
    isLoading: MutableState<Boolean>,
    goToMainScreen: () -> Unit,
    signInDTO: SignInDTO
) {
    authViewModel.loginUser(signInDTO = signInDTO)
    authViewModel.loginResult.observe(activity) {
        when (it) {
            is BaseResponse.Loading -> {
                isLoading.value = true
            }

            is BaseResponse.Success -> {
                isLoading.value = false
                if (!it.data?.token.isNullOrEmpty()) {
                    it.data?.token?.let { it1 -> SessionManager.saveAuthToken(activity, it1) }
                    goToMainScreen()
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
    authViewModel: AuthViewModel,
    activity: ComponentActivity,
    isLoading: MutableState<Boolean>,
    goToSignInPage: (email: String) -> Unit,
    signUpDTO: SignUpDTO
) {
    if (signUpDTO.Password != signUpDTO.RepeatPassword) {
        showToast(activity, "Passwords should be identical")
        return
    }

    authViewModel.signUpUser(signUpDTO = signUpDTO)
    authViewModel.signUpResult.observe(activity) {
        when (it) {
            is BaseResponse.Loading -> {
                isLoading.value = true
            }

            is BaseResponse.Success -> {
                isLoading.value = false
                showToast(activity, it.data?.message ?: "success")
                goToSignInPage(signUpDTO.Email)
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

/*
fun handleGoogleSignIn() {
    val coroutineScope = rememberCoroutineScope()
    val signInRequestCode = 1

    val authResultLauncher =
        rememberLauncherForActivityResult(AuthResultContract()) { task ->
            val account = task?.result
            if (account != null) {
                coroutineScope.launch {

                }
            }
        }

    val onGoogleLoginClicked = {
        authResultLauncher.launch(signInRequestCode)
    }
}*/
