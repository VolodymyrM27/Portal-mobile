package com.example.portal

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.portal.ui.theme.PortalTheme
import com.example.portal.viewmodels.AuthViewModel
import com.example.portal.viewmodels.LoginViewModel
import com.example.portal.viewmodels.MainPageViewModel
import com.example.portal.viewmodels.SignUpViewModel


class MainActivity : ComponentActivity() {
    private val mainPageViewModel by viewModels<MainPageViewModel>()
    private val authViewModel by viewModels<AuthViewModel>()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PortalTheme {
                PortalApp(activity = this, authViewModel = authViewModel)
            }
        }
    }
}