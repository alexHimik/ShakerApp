package com.shaker.app.presentation.ui.screen.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.shaker.app.presentation.ui.components.EmptyStubComponent
import com.shaker.app.presentation.ui.components.ShakerCard
import com.shaker.app.presentation.ui.components.ShakerSurface
import com.shaker.app.presentation.ui.theme.ShakerTheme
import com.shaker.app.presentation.ui.viewmodel.category.ShakerCategoryViewModel
import com.shaker.domain.model.ShakerCocktailCategoryModel
import com.shaker.domain.result.Failure

@Composable
fun ShakerCategoryCatalogScreen(
    onCategoryClick: (ShakerCocktailCategoryModel) -> Unit,
    paddingValues: PaddingValues,
    onErrorHandler: (LiveData<List<Failure>>) -> Unit,
    categoryViewModel: ShakerCategoryViewModel = viewModel(factory = ShakerCategoryViewModel.Factory)
) {
    val categoriesData by categoryViewModel.categoriesData.collectAsStateWithLifecycle()
    onErrorHandler.invoke(categoryViewModel.errorData.asLiveData())

    ShakerSurface(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)) {
        Column(
            verticalArrangement = getColumnVerticalAlignment(categoriesData.loading),
            modifier = Modifier.fillMaxSize()
        ) {
            if(categoriesData.loading) {
                CircularProgressIndicator(
                    color = ShakerTheme.colors.iconPrimary,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 6.dp)
                        .size(36.dp)
                )
            } else {
                if(categoriesData.categories.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(id = R.string.categories_label),
                        style = MaterialTheme.typography.h5,
                        color = ShakerTheme.colors.textHelp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    CategoriesGridComponent(categoriesData.categories, onCategoryClick)
                } else {
                    EmptyStubComponent(stringResource(id = R.string.no_categories_label))
                }
            }
        }
    }
}

@Composable
private fun CategoriesGridComponent(
    categories: List<ShakerCocktailCategoryModel>,
    onCategoryClick: (ShakerCocktailCategoryModel) -> Unit
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        itemsIndexed(categories) { index: Int, item: ShakerCocktailCategoryModel ->
            ShakerCard(
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        onCategoryClick.invoke(item)
                    },
                color = ShakerTheme.colors.brand
            ) {
                Text(
                    text = item.categoryName,
                    style = MaterialTheme.typography.h5,
                    color = ShakerTheme.colors.textHelp,
                    modifier = Modifier
                        .height(90.dp)
                        .padding(horizontal = 16.dp, vertical = 5.dp)
                        .wrapContentHeight()
                )
            }
        }
    }
}

private fun getColumnVerticalAlignment(isDataLoading: Boolean): Arrangement.Vertical {
    return if(isDataLoading) Arrangement.Center else Arrangement.Top
}