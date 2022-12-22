package com.example.portal.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.portal.YellowHeader
import com.example.portal.entities.dish.DishEntity
import com.example.portal.ui.theme.BackgroundGrey
import com.example.portal.ui.theme.BorderGreen
import com.example.portal.ui.theme.BorderYellow
import com.example.portal.ui.theme.Milk

@Composable
fun DishDescriptionPage(
    dishEntity: DishEntity, goBack: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundGrey)
    ) {
        item(1) {
            YellowHeader(text = dishEntity.Name, hasBackButton = true, onBackButtonClick = goBack)
        }

        items(1) {
            Card(
                backgroundColor = Milk,
                border = BorderStroke(width = 1.dp, color = BorderYellow),
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .height(250.dp),
                shape = RoundedCornerShape(15.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .size(180.dp)
                            .border(1.dp, BorderYellow, RoundedCornerShape(8.dp))
                            .clip(RoundedCornerShape(8.dp)),
                        painter = rememberAsyncImagePainter(dishEntity.Photo),
                        contentDescription = "photo",
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp),
                            backgroundColor = Milk,
                            border = BorderStroke(width = 1.dp, color = BorderYellow),
                            shape = RoundedCornerShape(15.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "калорійність",
                                    style = MaterialTheme.typography.body2
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = dishEntity.Caloricity.toString() + " ккал",
                                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }

            /* Card(
                 backgroundColor = Milk,
                 border = BorderStroke(width = 1.dp, color = BorderGreen),
                 modifier = Modifier
                     .padding(start = 15.dp, top = 15.dp, end = 15.dp)
                     .hei,
                 shape = RoundedCornerShape(15.dp)
             ) {
                 Column(
                     modifier = Modifier
                         .fillMaxSize(),
                     horizontalAlignment = Alignment.Start
                 ) {
                     Box(
                         modifier = Modifier
                             .fillMaxWidth()
                             .height(30.dp),
                         contentAlignment = Alignment.Center
                     ) {
                         Text(
                             text = "Інгредієнти",
                             style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold)
                         )
                     }

                     LazyColumn {
                         items(dishEntity.DishProducts) { product ->
                             Text(
                                 text = product.Product.Name + " - " + product.Amount * product.Product.Capacity,
                                 style = MaterialTheme.typography.body2
                             )
                         }
                     }
                 }
             }*/

            Card(
                backgroundColor = Milk,
                border = BorderStroke(width = 1.dp, color = BorderYellow),
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp, end = 15.dp),
                shape = RoundedCornerShape(15.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Етапи приготування",
                            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Column {
                        for (instruction in dishEntity.Instructions.sortedBy { x -> x.Step }) {
                            val checkedState = remember { mutableStateOf(false) }
                            Row(modifier = Modifier.padding(bottom = 20.dp)) {
                                Checkbox(
                                    checked = checkedState.value,
                                    onCheckedChange = { checkedState.value = it }
                                )


                                Text(
                                    text = buildAnnotatedString {
                                        append(instruction.Step.toString() + "   ")
                                        addStyle(
                                            style = MaterialTheme.typography.body1.copy(
                                                fontWeight = FontWeight.Bold
                                            )
                                                .toSpanStyle(), start = 0, end = 1
                                        )
                                        append(instruction.Description)
                                    },
                                    style = MaterialTheme.typography.body2,
                                    modifier = Modifier.padding(end = 20.dp)
                                )
                            }
                        }
                    }
                }
            }


            Box(modifier = Modifier.height(80.dp)){

            }

        }
    }
}