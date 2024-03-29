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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.portal.R
import com.example.portal.Routes
import com.example.portal.auth.SessionManager
import com.example.portal.entities.DietaryRestrictionEntity
import com.example.portal.entities.FridgeItem
import com.example.portal.ui.product.ProductScreen
import com.example.portal.ui.theme.BrightGreen
import com.example.portal.ui.theme.BrightYellow
import com.example.portal.viewmodels.DishViewModel
import com.example.portal.viewmodels.ProductViewModel

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun MainScreen(
    activity: ComponentActivity,
    dishViewModel: DishViewModel,
    productViewModel: ProductViewModel,
    goToAuthScreen: () -> Unit
) {
    val navController = rememberNavController()
    Box {
        Navigation(navController = navController,
            dishViewModel = dishViewModel,
            productViewModel = productViewModel,
            activity = activity,
            onSignOut = {
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
fun Navigation(
    navController: NavHostController,
    dishViewModel: DishViewModel,
    productViewModel: ProductViewModel,
    activity: ComponentActivity,
    onSignOut: () -> Unit
) {
    NavHost(navController, startDestination = Routes.DishScreen.route) {
        composable(route = Routes.DishScreen.route) {
            DishScreen(dishViewModel = dishViewModel, activity = activity, onSignOut = onSignOut)
        }
        composable(Routes.ProductScreen.route) {
            ProductScreen(productViewModel = productViewModel, activity = activity, onSignOut = onSignOut)
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
            Fridge(items = items,
                deleteItem = { id ->
                    items.removeIf { x ->
                        x.Id == id
                    }
                },
                editItem = { fridgeItem ->
                    val item = items.find { x ->
                        x.Id == fridgeItem.Id
                    }
                    item?.Amount = fridgeItem.Amount
                }
            )
        }
        composable(Routes.Basket.route) {
            Basket()
        }
        composable(Routes.Profile.route) {
            val restrictions = remember {
                mutableListOf(
                    DietaryRestrictionEntity(
                        Id = 1,
                        Title = "Вегетеріанство",
                        Image = R.drawable.ic_launcher_background
                    ),
                    DietaryRestrictionEntity(
                        Id = 2,
                        Title = "Халяль",
                        Image = R.drawable.ic_launcher_background
                    )
                )
            }
            DietaryRestrictions(
                restrictions = restrictions,
                deleteItem = { id ->
                    restrictions.removeIf { x ->
                        x.Id == id
                    }
                }
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, modifier: Modifier = Modifier) {
    val items = listOf(
        Routes.DishScreen,
        Routes.ProductScreen,
        Routes.Fridge,
        Routes.Basket,
        Routes.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation(
        backgroundColor = if (currentRoute == Routes.DishScreen.route) BrightYellow else BrightGreen,
        contentColor = Color.Black,
        modifier = modifier
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .clip(shape = RoundedCornerShape(15.dp))
    ) {

        items.forEach { item ->
            BottomNavigationItem(
                modifier = Modifier.padding(6.dp),
                icon = {
                    Icon(
                        painterResource(id = item.icon ?: 0),
                        contentDescription = item.title
                    )
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.3f),
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