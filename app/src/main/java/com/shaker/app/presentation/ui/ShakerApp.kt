package com.shaker.app.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shaker.app.R
import com.shaker.app.presentation.ui.navigation.ShakerNavController
import com.shaker.app.presentation.ui.navigation.ShakerScreen
import com.shaker.app.presentation.ui.navigation.rememberShakerNavController
import com.shaker.app.presentation.ui.theme.ShakerTheme

@Composable
fun ShakerApp() {
    ShakerTheme {
        val shakerNavController = rememberShakerNavController()
        val navigationTabs = listOf<ShakerScreen>(
            ShakerScreen.Main,
            ShakerScreen.Catalog,
            ShakerScreen.Favorites
        )
        Scaffold(
            bottomBar = { ShakerAppBottomNavigation(shakerNavController, navigationTabs) },
        ) {
            MainScreenNavigationResolver(shakerNavController)
        }
    }
}

@Composable
private fun MainScreenNavigationResolver(
    navController: ShakerNavController
) {
    NavHost(navController = navController.navController, startDestination = ShakerScreen.Main.route) {
        composable(ShakerScreen.Main.route) {
            TempShakerScreen(stringResource(id = ShakerScreen.Main.title))
        }
        composable(ShakerScreen.Catalog.route) {
            TempShakerScreen(stringResource(id = ShakerScreen.Catalog.title))
        }
        composable(ShakerScreen.Favorites.route) {
            TempShakerScreen(stringResource(id = ShakerScreen.Favorites.title))
        }
    }
}

@Composable
private fun TempShakerScreen(screenTitle: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.teal_700))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = screenTitle,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}

@Composable
private fun ShakerAppBottomNavigation(
    navController: ShakerNavController,
    items: List<ShakerScreen>
) {
    BottomNavigation {
        val currentRoute = navController.currentRoute
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { screen.icon?.let {
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
                        navController.navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}