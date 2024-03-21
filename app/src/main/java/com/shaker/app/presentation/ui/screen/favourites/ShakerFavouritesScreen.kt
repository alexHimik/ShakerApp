package com.shaker.app.presentation.ui.screen.favourites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shaker.app.presentation.ui.components.ShakerSurface
import com.shaker.app.presentation.ui.viewmodel.favourites.ShakerFavouritesViewModel
import com.shaker.domain.model.ShakerCocktailModel
import com.shaker.domain.result.Failure

@Composable
fun ShakerFavouritesScreen(
    onCocktailClick: (ShakerCocktailModel) -> Unit,
    paddingValues: PaddingValues,
    onErrorHandler: (LiveData<List<Failure>>) -> Unit,
    favouritesViewModel: ShakerFavouritesViewModel = viewModel(factory = ShakerFavouritesViewModel.Factory)
) {
    ShakerSurface(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

        }
    }
}