package com.example.portal.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.portal.Header
import com.example.portal.R
import com.example.portal.entities.FridgeItem
import androidx.compose.foundation.lazy.items
import com.example.portal.ui.theme.BackgroundGrey


@Composable
fun Fridge(
    items: List<FridgeItem>,
    deleteItem: (id: Int) -> Unit,
    editItem: (fridgeItem: FridgeItem) -> Unit
) {
    val showDeleteDialog = remember {
        mutableStateOf(false)
    }
    val itemToDeleteId = remember {
        mutableStateOf(-1)
    }
    val itemToDeleteTitle = remember {
        mutableStateOf("")
    }
    val showEditDialog = remember {
        mutableStateOf(false)
    }
    val itemToEdit = remember {
        mutableStateOf(
            FridgeItem(
                Id = -1,
                Title = "",
                Image = -1,
                UnitOfMeasurement = "",
                Amount = 0.0
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundGrey)
    ) {
        Header(text = stringResource(R.string.my_fridge))
        LazyColumn {
            items(items) { item ->
                FridgeItem(fridgeItem = item, deleteItem = { id, title ->
                    itemToDeleteId.value = id
                    itemToDeleteTitle.value = title
                    showDeleteDialog.value = true
                }, editItem = { fridgeItem ->
                    itemToEdit.value = fridgeItem
                    showEditDialog.value = true
                })
            }
        }
        if (showDeleteDialog.value) {
            ShowDeleteDialog(
                onConfirm = {
                    deleteItem(
                        itemToDeleteId.value
                    )
                    showDeleteDialog.value = false
                },
                onDismiss = {
                    showDeleteDialog.value = false
                },
                title = itemToDeleteTitle.value
            )
        }
        if (showEditDialog.value) {
            ShowEditDialog(
                fridgeItem = itemToEdit.value,
                onDismiss = {
                    showEditDialog.value = false
                },
                onConfirm = {
                    editItem(
                        itemToEdit.value.copy(Amount = it)
                    )
                    showEditDialog.value = false
                }
            )
        }
    }
}

@Composable
fun FridgeItem(
    fridgeItem: FridgeItem,
    deleteItem: (id: Int, title: String) -> Unit,
    editItem: (fridgeItem: FridgeItem) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 5.dp),
        shape = RoundedCornerShape(15.dp),
        backgroundColor = LightGreen
    ) {
        Box(
            modifier = Modifier
                .size(0.dp, 100.dp)
                .fillMaxWidth(),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = fridgeItem.Image),
                    contentDescription = "fridge_item",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(80.dp)
                        .clip(CircleShape)
                )
                Column(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = fridgeItem.Title,
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        maxLines = 2,
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = "Capacity: ${fridgeItem.Amount} ${fridgeItem.UnitOfMeasurement}",
                        modifier = Modifier.fillMaxWidth(0.8f),
                        style = MaterialTheme.typography.body2
                    )
                }
                Column {
                    IconButton(onClick = { editItem(fridgeItem) }) {
                        Icon(Icons.Filled.Edit, null)
                    }
                    IconButton(onClick = { deleteItem(fridgeItem.Id, fridgeItem.Title) }) {
                        Icon(Icons.Filled.Clear, null)
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShowDeleteDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String
) {
    Dialog(
        onDismissRequest = { onDismiss() },
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
            Column {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    BrightGreen
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(
                            text = "$title will be removed from your fridge",
                            style = MaterialTheme.typography.h5,
                            textAlign = TextAlign.Center
                        )
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
fun ShowEditDialog(
    onDismiss: () -> Unit,
    onConfirm: (capacityFromDialog: Double) -> Unit,
    fridgeItem: FridgeItem
) {
    val capacityNew = remember {
        mutableStateOf(fridgeItem.Amount.toBigDecimal().toPlainString())
    }
    Dialog(
        onDismissRequest = { onDismiss() },
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
            Column {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    BrightGreen
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(
                            text = fridgeItem.Title,
                            style = MaterialTheme.typography.h5,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        shape = RoundedCornerShape(0.dp),
                        value = capacityNew.value,
                        onValueChange = {
                            capacityNew.value = it
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.size(100.dp, 50.dp)
                    )
                    Text(
                        text = fridgeItem.UnitOfMeasurement,
                        style = MaterialTheme.typography.h6
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
                            try {
                                onConfirm(capacityNew.value.toDouble())
                            } catch (e: java.lang.NumberFormatException) {
                                onDismiss()
                            }

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