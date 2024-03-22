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
import com.shaker.app.presentation.ui.screen.cocktails.details.ShakerCocktailDetailScreen
import com.shaker.app.presentation.ui.screen.cocktails.list.ShakerCategoryCocktailsScreen
import com.shaker.app.presentation.ui.screen.favourites.ShakerFavouritesScreen
import com.shaker.app.presentation.ui.screen.home.ShakerHomeScreen.NAV_CATEGORIES_DEST_PARAM
import com.shaker.app.presentation.ui.screen.home.ShakerHomeScreen.NAV_CATEGORY_ID_PARAM
import com.shaker.app.presentation.ui.screen.home.ShakerHomeScreen.NAV_COCKTAIL_DETAILS_DEST_PARAM
import com.shaker.app.presentation.ui.screen.home.ShakerHomeScreen.NAV_COCKTAIL_ID_PARAM
import com.shaker.app.presentation.ui.screen.search.SearchScreen
import com.shaker.domain.result.Failure

val navigationTabs = listOf<ShakerScreen>(
    ShakerScreen.Main,
    ShakerScreen.Categories,
    ShakerScreen.Favorites
)

object ShakerHomeScreen {
    const val NAV_CATEGORIES_DEST_PARAM = "category"
    const val NAV_COCKTAIL_DETAILS_DEST_PARAM = "cocktailDetails"
    const val NAV_CATEGORY_ID_PARAM = "categoryId"
    const val NAV_COCKTAIL_ID_PARAM = "cocktailId"
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
        ShakerHomeScreenNavigationResolver(homeScreenNavController, rootNavController, paddingData, onErrorHandler)
    }
}

@Composable
private fun ShakerHomeScreenNavigationResolver(navController: NavHostController,
                                               rootNavController: ShakerNavController,
                                               paddingValues: PaddingValues,
                                               onErrorHandler: (LiveData<List<Failure>>) -> Unit) {
    NavHost(
        navController = navController,
        startDestination = ShakerScreen.Main.route,
    ) {
        composable(ShakerScreen.Main.route) {
            SearchScreen(
                onCocktailClick = { cocktail ->
                    navController.navigate("$NAV_COCKTAIL_DETAILS_DEST_PARAM/${cocktail.id}")
                },
                onNavigateToRoute = { route ->
                    navController.navigate(route)
                },
                paddingValues,
                onErrorHandler
            )
        }
        composable(ShakerScreen.Categories.route) {
            ShakerCategoryCatalogScreen(
                onCategoryClick = { category ->
                    val correctedName = category.categoryName
                        .replace(" ", "")
                        .replace("/", "_")
                    navController.navigate("$NAV_CATEGORIES_DEST_PARAM/$correctedName")
                },
                paddingValues,
                onErrorHandler
            )
        }
        composable(ShakerScreen.Favorites.route) {
            ShakerFavouritesScreen(
                onCocktailClick = { cocktail ->
                    navController.navigate("$NAV_COCKTAIL_DETAILS_DEST_PARAM/${cocktail.id}")
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
                    navController.navigate("$NAV_COCKTAIL_DETAILS_DEST_PARAM/${cocktail.id}")
                },
                paddingValues,
                onErrorHandler
            )
        }
        composable(
            ShakerScreen.CocktailDetails.route,
            arguments = listOf(navArgument(NAV_COCKTAIL_ID_PARAM) { type = NavType.StringType })
        ) {backStackEntry ->
            ShakerCocktailDetailScreen(
                backStackEntry.arguments?.getString(NAV_COCKTAIL_ID_PARAM),
                paddingValues,
                onErrorHandler,
                {
                    navController.popBackStack()
                    navController.navigate(ShakerScreen.Main.route)
                }
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
        var currentRoute = navController.currentDestination?.route
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
                        currentRoute = screen.route
                    }
                }
            )
        }
    }
}