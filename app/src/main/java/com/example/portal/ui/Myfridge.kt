package com.example.portal.ui

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
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
        FridgeItem(R.drawable.ic_launcher_background,"Смачні кадировці у власному соку",10.5)
    }
}

@Composable
fun FridgeItem(picture: Int, name: String, capacity: Double){
    var capacitynew = remember {
        mutableStateOf(capacity)
    }
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 15.dp, vertical = 5.dp),
        shape = RoundedCornerShape(15.dp),
        LightGreen) {
        Box(modifier = Modifier
            .size(0.dp, 100.dp)
            .fillMaxWidth(),
        ){
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(painter = painterResource(id = picture),
                    contentDescription = "fridgeitem",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(80.dp)
                        .clip(CircleShape),
                    )
                Column(
                    modifier = Modifier.padding(vertical = 10.dp),
                ) {
                    Text(text = "Смачні кадировці у власному соку", modifier =  Modifier.fillMaxWidth(0.8f), maxLines = 2, style = MaterialTheme.typography.h6)
                    Text(text = "Кількість: ${capacitynew.value} кг", modifier =  Modifier.fillMaxWidth(0.8f), style = MaterialTheme.typography.h6)
                }
                Column(
                    modifier = Modifier.padding(vertical = 10.dp),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(onClick = {
                        capacitynew.value += 1 ;
                    }) {
                        Icon(Icons.Filled.Edit,null)
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Clear,null)
                    }
                }
            }
        }
    }
}