package com.example.portal.ui.product

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.portal.GreenHeader
import com.example.portal.YellowHeader
import com.example.portal.entities.ProductEntity
import com.example.portal.ui.theme.BackgroundGrey
import com.example.portal.ui.theme.BorderGreen
import com.example.portal.ui.theme.Milk

@Composable
fun ProductDescriptionPage(
    productEntity: ProductEntity, goBack: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundGrey)
    ) {
        item(1) {
            GreenHeader(text = productEntity.Name, hasBackButton = true, onBackButtonClick = goBack)
        }

        items(1) {
            Card(
                backgroundColor = Milk,
                border = BorderStroke(width = 1.dp, color = BorderGreen),
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
                            .border(1.dp, BorderGreen, RoundedCornerShape(8.dp))
                            .clip(RoundedCornerShape(8.dp)),
                        painter = rememberAsyncImagePainter(productEntity.Photo),
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
                            border = BorderStroke(width = 1.dp, color = BorderGreen),
                            shape = RoundedCornerShape(15.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "ціна",
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
                                text = productEntity.Price.toString() + " грн",
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

            /*Card(
                backgroundColor = Milk,
                border = BorderStroke(width = 1.dp, color = BorderGreen),
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
                        for (instruction in productEntity.Instructions.sortedBy { x -> x.Step }) {
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
            }*/


            Box(modifier = Modifier.height(80.dp)) {

            }

        }
    }
}