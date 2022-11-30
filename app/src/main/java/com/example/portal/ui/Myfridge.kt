package com.example.portal.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.example.portal.ui.theme.BrightGreen
import com.example.portal.ui.theme.LightGreen
import com.example.portal.ui.theme.Gray800
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())

        ){

            FridgeItem(R.drawable.ic_launcher_background,"Смачні кадировці у власному соку",10.5,"kg")
            FridgeItem(R.drawable.ic_launcher_background,"Молоко",1.0,"l")
            FridgeItem(R.drawable.ic_launcher_background,"Смачні кадировці у власному соку",10.5,"kg")

        }
    }
}

@Composable
fun FridgeItem(picture: Int, name: String, capacity: Double, capacitySymbol: String){
    var capacitynew = remember {
        mutableStateOf(capacity)
    }
    var isreal = remember {
        mutableStateOf(true)
    }
    var deletedDalog = remember {
        mutableStateOf(false)
    }
    var editDialog = remember {
        mutableStateOf(false)
    }
    if (isreal.value){
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
                        Text(text = name, modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp), maxLines = 2, style = MaterialTheme.typography.h6)
                        Text(text = "Capacity: ${capacitynew.value} $capacitySymbol", modifier =  Modifier.fillMaxWidth(0.8f), style = MaterialTheme.typography.h6)
                    }
                    Column(
                        modifier = Modifier.padding(vertical = 10.dp),
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        IconButton(onClick = {
                            editDialog.value = true;
                        }) {
                            Icon(Icons.Filled.Edit,null)
                        }
                        IconButton(onClick = {
                            deletedDalog.value = true
                        }) {
                            Icon(Icons.Filled.Clear,null)
                        }
                    }
                }
                if(deletedDalog.value){
                    showDeleteDialog(
                        onDismiss = {
                            deletedDalog.value = false
                        },
                        onConfirm = {
                            isreal.value = false;
                            //отута вставити видалення з бази данних
                            deletedDalog.value = false
                        },
                        name = name
                    )
                }
                if(editDialog.value){
                    showEditDialog(
                        onDismiss = {
                            editDialog.value = false
                        },
                        {
                            capacityFromDialog -> capacityFromDialog
                            capacitynew.value = capacityFromDialog
                            //отута вставити оновлення бази данних
                            editDialog.value = false
                        },
                        name = name,
                        capacity = capacitynew.value,
                        capacitySymbol = capacitySymbol
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun showDeleteDialog(
    onDismiss:() -> Unit,
    onConfirm:() -> Unit,
    name: String
){
    Dialog(
        onDismissRequest = {onDismiss()},
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        )
    ) {
        Card(
            elevation = 5.dp,
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .width(250.dp)
        ) {
            Column(

            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    BrightGreen
                ) {
                    Box(modifier = Modifier
                        .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(text = "${name} will be removed from your fridge", style = MaterialTheme.typography.h5, textAlign = TextAlign.Center)
                    }


                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = {
                                  onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                           backgroundColor = Gray800
                        ),
                        shape = RoundedCornerShape(15.dp),
                    ) {
                        Text(
                            text = "Cancel",
                            style = MaterialTheme.typography.h6
                        )
                    }
                    Button(
                        onClick = {
                                  onConfirm()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = BrightGreen
                        ),
                        shape = RoundedCornerShape(15.dp),

                    ) {
                        Text(
                            text = "OK",
                            style = MaterialTheme.typography.h6
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun showEditDialog(
    onDismiss:() -> Unit,
    onConfirm:(capacityFromDialog: Double) -> Unit,
    name: String,
    capacity: Double,
    capacitySymbol: String
){
    var capacitynew = remember {
        mutableStateOf(capacity.toString())
    }
    var colorTextFieldBorder = remember {
        mutableStateOf(Color.Black)
    }
    var widhtTextFieldBorder = remember {
        mutableStateOf(-1.dp)
    }
    var errorMessage = remember {
        mutableStateOf("")
    }
    var length = 0;
    Dialog(
        onDismissRequest = {onDismiss()},
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        )
    ) {
        Card(
            elevation = 5.dp,
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .width(250.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    BrightGreen
                ) {
                    Box(modifier = Modifier
                        .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(text = "${name}", style = MaterialTheme.typography.h5, textAlign = TextAlign.Center)
                    }

                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(

                        shape = RoundedCornerShape(5.dp),
                        value = capacitynew.value,
                        onValueChange = {
                            length = it.length

                            capacitynew.value = it
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .size(100.dp, 50.dp)
                            .border(
                                width = widhtTextFieldBorder.value,
                                color = colorTextFieldBorder.value,
                                shape = RoundedCornerShape(5.dp),
                            )
                        )

                    Text(
                        text = capacitySymbol,
                        style = MaterialTheme.typography.h6
                    )
                }
                if(errorMessage.value!=""){
                    Text(
                        text = errorMessage.value,
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center,

                        )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = {
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Gray800
                        ),
                        shape = RoundedCornerShape(15.dp),
                    ) {
                        Text(
                            text = "Cancel",
                            style = MaterialTheme.typography.h6
                        )
                    }
                    Button(
                        onClick = {
                            if(length > 7){
                                colorTextFieldBorder.value = Color.Red
                                widhtTextFieldBorder.value = 2.dp
                                errorMessage.value = "The line is too long"
                            }else{
                                try {
                                    onConfirm(capacitynew.value.toDouble())
                                }catch (e: java.lang.NumberFormatException){
                                    colorTextFieldBorder.value = Color.Red
                                    widhtTextFieldBorder.value = 2.dp
                                    errorMessage.value = "The line does not contain a number"
                                }
                            }


                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = BrightGreen
                        ),
                        shape = RoundedCornerShape(15.dp),

                        ) {
                        Text(
                            text = "OK",
                            style = MaterialTheme.typography.h6,
                        )
                    }
                }
            }
        }
    }
}