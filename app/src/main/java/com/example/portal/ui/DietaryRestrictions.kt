package com.example.portal.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import com.example.portal.Header
import com.example.portal.R
import com.example.portal.dto.responses.RestrictionsResponse
import com.example.portal.entities.DietaryRestrictionEntity
import com.example.portal.ui.theme.BackgroundGrey
import com.example.portal.ui.theme.BrightGreen
import com.example.portal.ui.theme.Gray800
import com.example.portal.ui.theme.LightGreen

@Composable
fun DietaryRestrictions(
    restrictions: MutableState<List<RestrictionsResponse>>,
    deleteItem: (id: Int) -> Unit
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
    Box(){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BackgroundGrey)
        ) {
            Header(text = stringResource(R.string.my_dietary_restrictions))
            LazyColumn {
                items(restrictions.value) { restriction ->
                    DietaryRestriction(restriction = restriction, deleteItem = { id, title ->
                        itemToDeleteId.value = id
                        itemToDeleteTitle.value = title
                        showDeleteDialog.value = true
                    })
                }
            }
            if (showDeleteDialog.value) {
                DeleteRestrictionDialog(
                    title = itemToDeleteTitle.value,
                    onConfirm = {
                        deleteItem(
                            itemToDeleteId.value
                        )
                        showDeleteDialog.value = false
                    },
                    onDismiss = {
                        showDeleteDialog.value = false
                    }
                )
            }
        }


    }

}

@Composable
fun DietaryRestriction(
    restriction: RestrictionsResponse,
    deleteItem: (id: Int, title: String) -> Unit
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
                    painter = rememberAsyncImagePainter(restriction.photo),
                    contentDescription = "fridge_item",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(80.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = restriction.name,
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    maxLines = 2,
                    style = MaterialTheme.typography.body1
                )

                IconButton(onClick = {
                    deleteItem(restriction.id, restriction.name)
                }) {
                    Icon(Icons.Filled.Clear, null)
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DeleteRestrictionDialog(
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
                            text = "\"$title\" will be removed from your dietary restrictions",
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