package com.shaker.app.presentation.ui.screen.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shaker.app.R
import com.shaker.app.presentation.ui.components.CocktailsGridList
import com.shaker.app.presentation.ui.components.FoundCocktailsGridList
import com.shaker.app.presentation.ui.components.NoResults
import com.shaker.app.presentation.ui.components.ShakerDivider
import com.shaker.app.presentation.ui.components.ShakerSurface
import com.shaker.app.presentation.ui.navigation.ShakerScreen
import com.shaker.app.presentation.ui.screen.home.ShakerHomeScreen.NAV_COCKTAIL_DETAILS_DEST_PARAM
import com.shaker.app.presentation.ui.theme.ShakerTheme
import com.shaker.app.presentation.ui.viewmodel.search.ShakerSearchViewModel
import com.shaker.domain.model.ShakerCocktailModel
import com.shaker.domain.result.Failure

@Composable
fun SearchScreen(
    onCocktailClick: (ShakerCocktailModel) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    paddingValues: PaddingValues,
    onErrorHandler: (LiveData<List<Failure>>) -> Unit,
    searchViewModel: ShakerSearchViewModel = viewModel(factory = ShakerSearchViewModel.Factory)
) {
    val displayData by searchViewModel.displayResults.collectAsStateWithLifecycle()
    val queryData by searchViewModel.query.collectAsStateWithLifecycle()
    val searchProgressState by searchViewModel.searchProgress.collectAsStateWithLifecycle()

    onErrorHandler.invoke(searchViewModel.errorData.asLiveData())

    ShakerSurface(
        modifier = Modifier
        .wrapContentHeight()
        .padding(bottom = paddingValues.calculateBottomPadding())
    ) {
        Column {
            Spacer(modifier = Modifier.statusBarsPadding())
            SearchBar(
                query = TextFieldValue(text = queryData, selection = TextRange(queryData.length)),
                onQueryChange = { searchViewModel.onSearchChange(it.text) },
                searchFocused = queryData.isNotEmpty(),
                onSearchFocusChange = {  },
                onClearQuery = { searchViewModel.onSearchChange("") },
                searching = searchProgressState
            )
            ShakerDivider()

            when (displayData.resultsType) {
                ShakerSearchViewModel.SearchDisplay.Results -> {
                    FoundCocktailsGridList(displayData.results) {
                        onCocktailClick.invoke(it)
                    }
                    onNavigateToRoute.invoke(
                        "${NAV_COCKTAIL_DETAILS_DEST_PARAM}/${displayData.results[0].id}"
                    )
                }

                ShakerSearchViewModel.SearchDisplay.Viewed -> {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = stringResource(R.string.recently_viewed_label),
                        style = MaterialTheme.typography.h6,
                        color = ShakerTheme.colors.textHelp
                    )
                    CocktailsGridList(
                        displayData.results, onCocktailClick
                    )
                }

                ShakerSearchViewModel.SearchDisplay.NoResults -> NoResults(queryData)
            }
        }
    }
}

@Composable
private fun SearchBar(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    searchFocused: Boolean,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
    searching: Boolean,
    modifier: Modifier = Modifier
) {
    ShakerSurface(
        color = ShakerTheme.colors.uiFloated,
        contentColor = ShakerTheme.colors.textSecondary,
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            if (query.text.isEmpty()) {
                SearchHint()
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight()
            ) {
                if (searchFocused) {
                    IconButton(onClick = onClearQuery) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            tint = ShakerTheme.colors.iconPrimary,
                            contentDescription = stringResource(R.string.label_back)
                        )
                    }
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    textStyle = TextStyle.Default.copy(color = Color.White),
                    modifier = Modifier
                        .weight(1f)
                        .onFocusChanged {
                            onSearchFocusChange(it.isFocused)
                        }
                )
                if (searching) {
                    CircularProgressIndicator(
                        color = ShakerTheme.colors.iconPrimary,
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .size(36.dp)
                    )
                } else {
                    Spacer(Modifier.width(IconSize)) // balance arrow icon
                }
            }
        }
    }
}

private val IconSize = 48.dp

@Composable
private fun SearchHint() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            tint = ShakerTheme.colors.textHelp,
            contentDescription = stringResource(R.string.label_search)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.search_cocktail),
            color = ShakerTheme.colors.textHelp
        )
    }
}