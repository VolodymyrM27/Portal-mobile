package com.example.portal.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.portal.CustomCircularProgressIndicator
import com.example.portal.StyledButton
import com.example.portal.R
import com.example.portal.responses.UserResponse


@Composable
fun MainPage(userResponse: UserResponse?, onLogOutClick: () -> Unit, isLoading: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(10.dp)
    ) {

        Row {
            Text("Hello", style = MaterialTheme.typography.h1)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            Text(userResponse?.name ?: "anonymous", style = MaterialTheme.typography.h1)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            Text(userResponse?.email ?: "-", style = MaterialTheme.typography.h1)
        }

        StyledButton(onClick = onLogOutClick, icon = {
            androidx.compose.material.Icon(
                imageVector = Icons.Rounded.Lock, contentDescription = "lock"
            )
        }, textId = R.string.logout)
    }
    if (isLoading) {
        CustomCircularProgressIndicator()
    }
}