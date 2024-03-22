package com.shaker.app.presentation.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.shaker.app.R

sealed class ShakerScreen(val route: String, @StringRes val title: Int, val icon: ImageVector) {

    object OnBoarding : ShakerScreen(
        "onboarding",
        R.string.onboarding_screen_name_label,
        Icons.Default.Home
    )
    object HomeRoot : ShakerScreen(
        "home",
        R.string.root_home_screen_name_label,
        Icons.Default.Home
    )
    object Main : ShakerScreen(
        "main",
        R.string.main_screen_name_label,
        Icons.Default.Home
    )
    object Categories : ShakerScreen(
        "category",
        R.string.catalog_screen_name_label,
        Icons.Default.List
    )
    object Favorites : ShakerScreen(
        "favorites",
        R.string.favorites_screen_name_label,
        Icons.Default.Favorite
    )

    object CategoryCocktails : ShakerScreen(
        "category/{categoryId}",
        R.string.category_cocktails_screen_name_label,
        Icons.Default.List
    )

    object CocktailDetails : ShakerScreen(
        "cocktailDetails/{cocktailId}",
        R.string.cocktail_details_screen_name_label,
        Icons.Default.List
    )
}