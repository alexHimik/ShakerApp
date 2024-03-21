package com.shaker.app.presentation.ui.screen.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shaker.app.R
import com.shaker.app.presentation.ui.navigation.ShakerNavController
import com.shaker.app.presentation.ui.navigation.ShakerScreen
import com.shaker.app.presentation.ui.screen.category.ShakerCategoryCatalogScreen
import com.shaker.app.presentation.ui.screen.cocktails.ShakerCategoryCocktailsScreen
import com.shaker.app.presentation.ui.screen.favourites.ShakerFavouritesScreen
import com.shaker.app.presentation.ui.screen.home.ShakerHomeScreen.NAV_CATEGORIES_DEST_PARAM
import com.shaker.app.presentation.ui.screen.home.ShakerHomeScreen.NAV_CATEGORY_ID_PARAM
import com.shaker.app.presentation.ui.screen.search.SearchScreen
import com.shaker.domain.result.Failure

val navigationTabs = listOf<ShakerScreen>(
    ShakerScreen.Main,
    ShakerScreen.Categories,
    ShakerScreen.Favorites
)

object ShakerHomeScreen {
    const val NAV_CATEGORIES_DEST_PARAM = "category"
    const val NAV_CATEGORY_ID_PARAM = "categoryId"
}

@Composable
fun ShakerHomeScreen(
    rootNavController: ShakerNavController,
    onErrorHandler: (LiveData<List<Failure>>) -> Unit
) {
    val homeScreenNavController = rememberNavController()
    Scaffold(
        bottomBar = { ShakerAppBottomNavigation(homeScreenNavController, navigationTabs) },
        ) { paddingData ->
        ShakerHomeScreenNavigationResolver(homeScreenNavController, paddingData, onErrorHandler)
    }
}

@Composable
private fun ShakerHomeScreenNavigationResolver(navController: NavHostController,
                                               paddingValues: PaddingValues,
                                               onErrorHandler: (LiveData<List<Failure>>) -> Unit) {
    NavHost(navController = navController, startDestination = ShakerScreen.Main.route) {
        composable(ShakerScreen.Main.route) {
            SearchScreen(
                onCocktailClick = { cocktail ->
                                  //todo implement cocktail clicks
                },
                onNavigateToRoute = { route ->

                },
                paddingValues,
                onErrorHandler
            )
        }
        composable(ShakerScreen.Categories.route) {
            ShakerCategoryCatalogScreen(
                onCategoryClick = { category ->
                    navController.navigate("$NAV_CATEGORIES_DEST_PARAM/${category.categoryName}")
                },
                paddingValues,
                onErrorHandler
            )
        }
        composable(ShakerScreen.Favorites.route) {
            ShakerFavouritesScreen(
                onCocktailClick = { cocktail ->
                    //todo implement cocktail clicks
                },
                paddingValues,
                onErrorHandler
            )
        }
        composable(
            ShakerScreen.CategoryCocktails.route,
            arguments = listOf(navArgument(NAV_CATEGORY_ID_PARAM) { type = NavType.StringType })
        ) { backStackEntry ->
            ShakerCategoryCocktailsScreen(
                backStackEntry.arguments?.getString(NAV_CATEGORY_ID_PARAM),
                onCocktailClick = { cocktail ->
                    //todo implement cocktail clicks
                },
                paddingValues,
                onErrorHandler
            )
        }
    }
}

@Composable
private fun ShakerAppBottomNavigation(
    navController: NavHostController,
    items: List<ShakerScreen>
) {
    BottomNavigation {
        val currentRoute = navController.currentDestination?.route
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { screen.icon.let {
                    Icon(
                        imageVector = it,
                        contentDescription = "",
                        modifier = Modifier.size(35.dp),
                    )
                } },
                label = { Text(stringResource(id = screen.title)) },
                selected = currentRoute == screen.route,
                selectedContentColor = Color(R.color.purple_700),
                unselectedContentColor = Color.White.copy(alpha = 0.4f),
                alwaysShowLabel = false,
                onClick = {
                    // This if check gives us a "singleTop" behavior where we do not create a
                    // second instance of the composable if we are already on that destination
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}