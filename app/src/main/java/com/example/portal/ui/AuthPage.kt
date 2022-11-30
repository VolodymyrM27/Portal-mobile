package com.example.portal.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.portal.google.AuthViewModel
import com.example.portal.R
import com.example.portal.StyledButton
import com.example.portal.google.AuthResultContract
import com.example.portal.ui.theme.Green100
import kotlinx.coroutines.launch


@Composable
fun AuthPage(
    authViewModel: AuthViewModel,
    onLoginClicked: () -> Unit = {},
    onRegisterClicked: () -> Unit = {},
    onGoogleLoggedIn: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val signInRequestCode = 1

    val authResultLauncher =
        rememberLauncherForActivityResult(AuthResultContract()) { task ->
            val account = task?.result
            if (account != null) {
                coroutineScope.launch {
//                    authViewModel.signIn(
//                        email = account.email ?: "",
//                        displayName = account.displayName ?: "",
//                    )
                    onGoogleLoggedIn()
                }
            }
        }

    val onGoogleLoginClicked = {
        authResultLauncher.launch(signInRequestCode)
    }

    Box(Modifier.background(Green100)) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 50.dp, vertical = 120.dp)
                .align(Alignment.Center), shape = RoundedCornerShape(50.dp)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(10.dp)
            ) {

                Row {
                    Text(stringResource(R.string.welcome), style = MaterialTheme.typography.h1)

                }

                Spacer(modifier = Modifier.height(20.dp))

                StyledButton(
                    onClick = onGoogleLoginClicked, icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.icons8_google),
                            contentDescription = "",
                            modifier = Modifier.size(48.dp)
                        )
                    }, textId = R.string.continue_with_google
                )

                Spacer(modifier = Modifier.height(140.dp))

                StyledButton(
                    onClick = onRegisterClicked, icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.signup_icon),
                            contentDescription = "",
                            modifier = Modifier.size(48.dp)
                        )

                    }, textId = R.string.sign_up
                )

                Spacer(modifier = Modifier.height(60.dp))

                StyledButton(
                    onClick = onLoginClicked, icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.input_icon),
                            contentDescription = "",
                            modifier = Modifier.size(48.dp)
                        )

                    }, textId = R.string.sign_in_with_portal
                )
            }

        }

    }
}