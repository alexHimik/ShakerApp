package com.shaker.app.presentation.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.shaker.app.R

sealed class ShakerScreen(val route: String, @StringRes val title: Int, val icon: ImageVector) {
    object Main : ShakerScreen("main", R.string.main_screen_name_label, Icons.Default.Home)
    object Catalog : ShakerScreen("catalog", R.string.catalog_screen_name_label, Icons.Default.List)
    object Favorites : ShakerScreen("favorites", R.string.favorites_screen_name_label,Icons.Default.Favorite)

}