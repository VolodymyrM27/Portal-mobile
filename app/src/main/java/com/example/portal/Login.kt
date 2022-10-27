package com.example.portal

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.portal.ui.theme.PortalTheme
import com.example.portal.ui.theme.Purple700

@OptIn(ExperimentalGraphicsApi::class)
@Composable
fun LoginPage(navController: NavHostController) {

    val shape = CircleShape

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.hsl(108F, 0.29F, 0.82F)))
    {
        Box(modifier = Modifier
            .width(300.dp)
            .height(600.dp)
            .background(Color.hsl(74F, 0.98F, 0.76F, 0.4F))
            .align(Alignment.Center)

        )
        {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row() {
                    Text(text = "Sign In")
                }
                Spacer(modifier = Modifier.height(20.dp))


            val login = remember { mutableStateOf(TextFieldValue()) }
            val password = remember { mutableStateOf(TextFieldValue()) }

            Spacer(modifier = Modifier.height(20.dp))
            Row() {
                TextField(
                    leadingIcon = {
                        androidx.compose.material.Icon(imageVector = Icons.Rounded.Person, contentDescription = "")
                    },
                    shape = RoundedCornerShape(50.dp),
                    label = { Text(text = "Login") },
                    value = login.value,
                    onValueChange = { login.value = it })
            }
            Spacer(modifier = Modifier.height(40.dp))
            Row() {
                TextField(
                    leadingIcon = {
                        androidx.compose.material.Icon(imageVector = Icons.Rounded.Lock, contentDescription = "")
                    },
                    shape = RoundedCornerShape(50.dp),
                    label = { Text(text = "Password") },
                    value = password.value,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    onValueChange = { password.value = it })
                }


            Spacer(modifier = Modifier.height(40.dp))
                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.hsl(58F, 0.52F,0.89F),
                            contentColor = Color.Black)
                    ) {
                        Icon(painter = painterResource(id = R.drawable.input_icon), contentDescription = "", modifier = Modifier.size(48.dp))
                        Text(text = "Sign In")
                    }
                }
            }
        }

    }
}
