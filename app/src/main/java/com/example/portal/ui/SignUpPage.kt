package com.example.portal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.lifecycle.viewModelScope
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.portal.CustomCircularProgressIndicator
import com.example.portal.R
import com.example.portal.StyledButton
import com.example.portal.TextEditField
import com.example.portal.dto.SignUpDTO
import com.example.portal.dto.requests.auth.BaseResponse
import com.example.portal.dto.responses.FridgeResponse
import com.example.portal.dto.responses.UserResponse
import com.example.portal.repositories.UserRepository
import com.example.portal.ui.theme.Green100
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun SignUpPage(onSignUpClick: (signUpDTO: SignUpDTO) -> Unit, isLoading: Boolean) {
    val focusManager = LocalFocusManager.current



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
                modifier = Modifier.padding(15.dp)
            ) {

                Row {
                    Text(
                        stringResource(R.string.creating_new_account),
                        style = MaterialTheme.typography.h1,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                var email by remember { mutableStateOf("") }
                var name by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var repeatPassword by remember { mutableStateOf("") }
                val signUpDTO = SignUpDTO(
                    Email = email,
                    Name = name,
                    Password = password,
                    RepeatPassword = repeatPassword
                )

                TextEditField(
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Email, contentDescription = "email"
                        )
                    },
                    labelId = R.string.email,
                    value = email,
                    onValueChange = { email = it },
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(
                            FocusDirection.Down
                        )
                    }),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextEditField(
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Person, contentDescription = "person"
                        )
                    },
                    labelId = R.string.name,
                    value = name,
                    onValueChange = { name = it },
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(
                            FocusDirection.Down
                        )
                    })
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextEditField(
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Lock, contentDescription = "lock"
                        )
                    },
                    labelId = R.string.password,
                    value = password,
                    onValueChange = { password = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(
                            FocusDirection.Down
                        )
                    }),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextEditField(
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Lock, contentDescription = "lock"
                        )
                    },
                    labelId = R.string.repeat_password,
                    value = repeatPassword,
                    onValueChange = { repeatPassword = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password, imeAction = ImeAction.Go
                    ),
                    keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus() }),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(40.dp))

                StyledButton(onClick = {
                    onSignUpClick(signUpDTO)
                }, icon = {
                    Icon(
                        painter = painterResource(R.drawable.signup_icon),
                        contentDescription = "sign up",
                        modifier = Modifier.size(32.dp)
                    )
                }, textId = R.string.create_new_account
                )
            }

        }

    }
    if (isLoading) {
        CustomCircularProgressIndicator()
    }
}
