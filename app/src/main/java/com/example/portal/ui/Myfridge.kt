package com.example.portal.ui

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.portal.ui.theme.BrightGreen
import com.example.portal.ui.theme.LightGreen
import androidx.compose.ui.unit.dp
import com.example.portal.R


@Composable
fun MyFridge(userLogin: String){
    Column(
        modifier = Modifier.fillMaxSize(),

    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 15.dp),
                shape = RoundedCornerShape(15.dp),
                BrightGreen
        ) {
            Box(modifier = Modifier
                .size(0.dp, 50.dp)
                .fillMaxWidth(),
                contentAlignment = Alignment.Center
            )
            {
                Text(text = "Fridge", style = MaterialTheme.typography.h5)
            }


        }
        FridgeItem()
    }
}

@Composable
fun FridgeItem(){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 15.dp, vertical = 5.dp),
        shape = RoundedCornerShape(15.dp),
        LightGreen) {
        Box(modifier = Modifier
            .size(0.dp, 100.dp)
            .fillMaxWidth(),
        ){
            Row() {
                Image(painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "fridgeitem",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(80.dp)
                        .clip(CircleShape)
                    )
                Column(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Text(text = "Смачні кадировці у власному соку", maxLines = 2, style = MaterialTheme.typography.h6)
                    Text(text = "Кількість: 10,5 кг", style = MaterialTheme.typography.h6)
                }
            }
        }
    }
}