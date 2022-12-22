package com.example.portal.ui


import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.portal.CustomCircularProgressIndicator
import com.example.portal.Routes
import com.example.portal.R
import com.example.portal.dto.requests.auth.SessionManager
import com.example.portal.dto.responses.FridgeResponse
import com.example.portal.dto.responses.RestrictionsResponse
import com.example.portal.entities.FridgeItem
import com.example.portal.repositories.UserRepository
import com.example.portal.ui.theme.BrightGreen
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun MainScreen(activity: ComponentActivity, goToAuthScreen: () -> Unit) {
    val navController = rememberNavController()
    Box {
        Navigation(navController = navController, onSignOut = {
            SessionManager.clearData(activity)
            goToAuthScreen()
        })
        BottomNavigationBar(
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun Navigation(navController: NavHostController, onSignOut: () -> Unit) {
    NavHost(navController, startDestination = Routes.Dishes.route) {
        composable(Routes.Dishes.route) {
            MainPage(onSignOutClick = onSignOut)
        }
        composable(Routes.Products.route) {
            ProductsScreen()
        }
        composable(Routes.Fridge.route) {
            val items = remember {
                mutableListOf(
                    FridgeItem(
                        Id = 1,
                        Title = "Смачні томати у власному соку",
                        Amount = 10.5,
                        UnitOfMeasurement = "kg",
                        Image = R.drawable.ic_launcher_background
                    ),
                    FridgeItem(
                        Id = 2,
                        Title = "Молоко",
                        Amount = 1.0,
                        UnitOfMeasurement = "l",
                        Image = R.drawable.ic_launcher_background
                    ),
                    FridgeItem(
                        Id = 3,
                        Title = "Смачні томати у власному соку",
                        Amount = 10.5,
                        UnitOfMeasurement = "kg",
                        Image = R.drawable.ic_launcher_background
                    )
                )
            }


            val context = LocalContext.current
            MyFridge("Bearer " + SessionManager.getToken(context))
        }
        composable(Routes.Basket.route) {
            Basket()
        }
        composable(Routes.Profile.route) {
            val restrictions = remember {
                mutableStateOf(emptyList<RestrictionsResponse>())
            }

            val loading = remember {
                mutableStateOf(true)
            }

            val repo = UserRepository()
            val context = LocalContext.current
            produceState<List<RestrictionsResponse>>(
                initialValue = emptyList(),
                producer = {

                    loading.value = true
                    val response = repo.getRestrictions("Bearer " + SessionManager.getToken(context))

                    try {

                        restrictions.value = response?.body()!!
                    }catch(ex: Exception){}
                    loading.value = false

                })

            val coroutineScope = rememberCoroutineScope()
            fun delete(id: Int){

                coroutineScope.launch{
                    loading.value = true
                    restrictions.value = repo.deleteRestriction("Bearer " + SessionManager.getToken(context),id)?.body()!!
                    loading.value = false
                }
            }


            DietaryRestrictions(
                restrictions = restrictions,
                deleteItem = {id ->
                    delete(id)
                },
                loading.value
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, modifier: Modifier = Modifier) {
    val items = listOf(
        Routes.Dishes,
        Routes.Products,
        Routes.Fridge,
        Routes.Basket,
        Routes.Profile
    )
    BottomNavigation(
        backgroundColor = BrightGreen,
        //contentColor = Color.White,
        modifier = modifier
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .clip(shape = RoundedCornerShape(15.dp))
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon ?: 0),
                        contentDescription = item.title
                    )
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {

                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }

                        launchSingleTop = true

                        restoreState = true
                    }
                }

            )
        }
    }
}

//@Composable
//fun TopBar() {
//    var search by remember { mutableStateOf("") }
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(10.dp),
//        backgroundColor = BrightGreen,
//        shape = RoundedCornerShape(10.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
//                .fillMaxWidth()
//        ) {
//            TextEditField(
//                icon = {
//                    Icon(
//                        imageVector = Icons.Rounded.Search, contentDescription = "search"
//                    )
//                },
//                labelId = R.string.search,
//                value = search,
//                onValueChange = { search = it }
//            )
//
//        }
//    }
//}