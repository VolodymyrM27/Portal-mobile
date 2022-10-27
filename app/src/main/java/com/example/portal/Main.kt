package com.example.portal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@OptIn(ExperimentalGraphicsApi::class)
@Composable
fun MainPage(navController: NavHostController) {

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
                Spacer(modifier = Modifier.height(70.dp))
                Row() {
                    Text(text = "WELCOME")
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(horizontalArrangement = Arrangement.Center) {
                    OutlinedButton(
                        shape = RoundedCornerShape(50.dp),
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.hsl(58F, 0.52F,0.89F),
                            contentColor = Color.Black
                        )
                    ) {
                        Icon(painter = painterResource(id = R.drawable.icons8_google), contentDescription = "", modifier = Modifier.size(48.dp))
                        Text(text = "Continue with Google")
                    }
                }
                Spacer(modifier = Modifier.height(140.dp))

                Button(
                    shape = RoundedCornerShape(50.dp),
                    onClick = { navController.navigate("Register") },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.hsl(58F, 0.52F,0.89F),
                        contentColor = Color.Black
                    ),
                ) {
                    Icon(painter = painterResource(id = R.drawable.signup_icon), contentDescription = "", modifier = Modifier.size(48.dp))
                    Text(text = "Sign Up")
                }
                Spacer(modifier = Modifier.height(60.dp))
                Button(
                    shape = RoundedCornerShape(50.dp),
                    onClick = {navController.navigate("Login")},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.hsl(58F, 0.52F,0.89F),
                        contentColor = Color.Black
                    )
                ) {
                    Icon(painter = painterResource(id = R.drawable.input_icon), contentDescription = "", modifier = Modifier.size(48.dp))
                    Text(text = "Sign in with Portal")
                }
            }
        }
    }
}
