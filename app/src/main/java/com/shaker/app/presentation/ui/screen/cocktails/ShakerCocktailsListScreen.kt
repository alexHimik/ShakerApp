package com.shaker.app.presentation.ui.screen.cocktails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shaker.app.R
import com.shaker.app.presentation.ui.components.CocktailInfoItem
import com.shaker.app.presentation.ui.components.EmptyStubComponent
import com.shaker.app.presentation.ui.components.ShakerSurface
import com.shaker.app.presentation.ui.theme.ShakerTheme
import com.shaker.app.presentation.ui.viewmodel.cocktails.ShakerCocktailsListViewModel
import com.shaker.domain.model.ShakerCocktailModel
import com.shaker.domain.result.Failure

@Composable
fun ShakerCategoryCocktailsScreen(
    selectedCategoryId: String?,
    onCocktailClick: (ShakerCocktailModel) -> Unit,
    paddingValues: PaddingValues,
    onErrorHandler: (LiveData<List<Failure>>) -> Unit,
    categoryCocktailsViewModel: ShakerCocktailsListViewModel = viewModel(factory = ShakerCocktailsListViewModel.Factory)
) {
    val cocktailsListData by categoryCocktailsViewModel.categoryCocktails.collectAsStateWithLifecycle()
    onErrorHandler.invoke(categoryCocktailsViewModel.errorData.asLiveData())

    categoryCocktailsViewModel.getCocktailsForCategory(selectedCategoryId.orEmpty())

    ShakerSurface(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)) {
        Column(
            verticalArrangement = getColumnVerticalAlignment(cocktailsListData.loading),
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.statusBarsPadding())

            if(cocktailsListData.loading) {
                CircularProgressIndicator(
                    color = ShakerTheme.colors.iconPrimary,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 6.dp)
                        .size(36.dp)
                )
            } else {
                if(cocktailsListData.cocktails.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = selectedCategoryId.orEmpty(),
                        style = MaterialTheme.typography.h5,
                        color = ShakerTheme.colors.textHelp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    CategoryCocktailsGridComponent(cocktailsListData.cocktails, onCocktailClick)
                } else {
                    EmptyStubComponent(stringResource(id = R.string.no_categories_label, ""))
                }
            }
        }
    }
}

@Composable
private fun CategoryCocktailsGridComponent(
    cocktails: List<ShakerCocktailModel>,
    onCocktailClick: (ShakerCocktailModel) -> Unit
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        itemsIndexed(cocktails) { index: Int, item: ShakerCocktailModel ->
            CocktailInfoItem(
                modifier = Modifier.padding(5.dp),
                cocktail = item,
                gradient = ShakerTheme.colors.gradient3_2,
                onCocktailClick = onCocktailClick
            )
        }
    }
}

private fun getColumnVerticalAlignment(isDataLoading: Boolean): Arrangement.Vertical {
    return if(isDataLoading) Arrangement.Center else Arrangement.Top
}