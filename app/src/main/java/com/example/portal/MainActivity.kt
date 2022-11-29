package com.example.portal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.portal.ui.theme.PortalTheme
import com.example.portal.viewmodels.LoginViewModel
import com.example.portal.viewmodels.MainPageViewModel
import com.example.portal.viewmodels.SignUpViewModel


class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<LoginViewModel>()
    private val mainPageViewModel by viewModels<MainPageViewModel>()
    private val signUpViewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PortalTheme {
                PortalApp(activity = this, loginViewModel = viewModel, mainPageViewModel = mainPageViewModel, signUpViewModel = signUpViewModel)
            }
        }
    }
}