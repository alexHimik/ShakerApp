package com.shaker.app.presentation.ui.screen.cocktails.details

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.shaker.app.R
import com.shaker.app.presentation.ui.components.EmptyStubComponent
import com.shaker.app.presentation.ui.components.ShakerSurface
import com.shaker.app.presentation.ui.theme.SemiTransparentBlack
import com.shaker.app.presentation.ui.theme.ShakerTheme
import com.shaker.app.presentation.ui.viewmodel.cocktails.details.ShakerCocktailDetailsViewModel
import com.shaker.domain.model.ShakerCocktailModel
import com.shaker.domain.result.Failure

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShakerCocktailDetailScreen(
    cocktailId: String?,
    paddingValues: PaddingValues,
    onErrorHandler: (LiveData<List<Failure>>) -> Unit,
    onBackPressHandler: () -> Unit,
    cocktailDetailsViewModel: ShakerCocktailDetailsViewModel = viewModel(factory = ShakerCocktailDetailsViewModel.Factory)
) {
    val cocktailData by cocktailDetailsViewModel.cocktailData.collectAsStateWithLifecycle()
    onErrorHandler.invoke(cocktailDetailsViewModel.errorData.asLiveData())

    val refreshing by cocktailDetailsViewModel.isRefreshing.collectAsStateWithLifecycle()
    val favourite by cocktailDetailsViewModel.isFavourite.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(refreshing && !cocktailData.loading,
        { cocktailDetailsViewModel.getRandomCocktailDetails() }
    )

    cocktailDetailsViewModel.getCocktailDetails(cocktailId)

    ShakerSurface(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = paddingValues.calculateBottomPadding())
    )
        {
        Box(
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                verticalArrangement = getColumnVerticalAlignment(cocktailData.loading),
                horizontalAlignment = if(cocktailData.loading) Alignment.CenterHorizontally else Alignment.Start,
                modifier = Modifier
                    .wrapContentHeight()
                    .pullRefresh(pullRefreshState)
            ) {
                if(cocktailData.loading) {
                    item {
                        CircularProgressIndicator(
                            color = ShakerTheme.colors.iconPrimary,
                            modifier = Modifier
                                .padding(top = 34.dp)
                                .size(36.dp)
                        )
                    }

                } else {
                    if(cocktailData.cocktail != null) {
                        item {
                            cocktailProtoDetailsComponent(
                                cocktailDetailsViewModel,
                                cocktailData.cocktail!!,
                                favourite,
                                onBackPressHandler
                            )
                        }
                        item {
                            cocktailServingDetails(
                                cocktailData.cocktail!!
                            )
                        }
                        item {
                            cocktailIngredientDetails(
                                cocktailData.cocktail!!
                            )
                        }
                        item {
                            cocktailPreparingDetails(
                                cocktailData.cocktail!!
                            )
                        }
                    } else {
                        item {
                            EmptyStubComponent(stringResource(id = R.string.no_coctail_data_label))
                        }
                    }
                }
            }
            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = refreshing,
                state = pullRefreshState
            )
        }
    }
}

@Composable
private fun cocktailPreparingDetails(
    cocktail: ShakerCocktailModel
) {
    Column(
        modifier = Modifier
            .padding(
                start = 10.dp,
                end = 10.dp,
                top = 16.dp
            )
    ) {
        Text(
            style = MaterialTheme.typography.subtitle1,
            color = Color.White,
            text = stringResource(R.string.prepairing_notes_label)
        )
        Text(
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    bottom = 16.dp
                ),
            style = MaterialTheme.typography.body2,
            color = Color.White,
            text = cocktail.preparingInstruction
        )
    }
}

@Composable
private fun cocktailIngredientDetails(
    cocktail: ShakerCocktailModel
) {
    Column(
        modifier = Modifier.
        padding(
            top = 10.dp,
            start = 10.dp,
            end = 10.dp
        )
    ) {
        Text(
            style = MaterialTheme.typography.body2,
            color = Color.White,
            text = stringResource(R.string.ingredients_label),
            modifier = Modifier
                .padding(
                    bottom = 10.dp
                )
        )
        cocktail.ingredients.forEach { ingredient ->
            if(ingredient.name.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .padding(
                            top = 8.dp
                        )
                ) {
                    Text(
                        style = MaterialTheme.typography.body2,
                        color = Color.White,
                        text = ingredient.name
                    )
                    Text(
                        modifier = Modifier
                            .padding(
                                start = 16.dp
                            ),
                        style = MaterialTheme.typography.body2,
                        color = Color.White,
                        text = ingredient.requiredAmount
                    )
                }
            }
        }
    }
}

@Composable
private fun cocktailServingDetails(
    cocktail: ShakerCocktailModel
) {
    Column(
        modifier = Modifier
            .padding(
                start = 10.dp,
                end = 10.dp
            )
    ) {
        Row(
            modifier = Modifier
                .padding(
                    top = 8.dp
                )
        ) {
            Text(
                style = MaterialTheme.typography.body2,
                color = Color.White,
                text = stringResource(R.string.id_label, cocktail.id)
            )
        }
        Row(
            modifier = Modifier
                .padding(
                    top = 8.dp
                )
        ) {
            Text(
                style = MaterialTheme.typography.body2,
                color = Color.White,
                text = stringResource(R.string.category_label, cocktail.category)
            )
        }
        Row(
            modifier = Modifier.padding(
                top = 8.dp
            )
        ) {
            Text(
                style = MaterialTheme.typography.body2,
                color = Color.White,
                text = stringResource(R.string.type_label, cocktail.type)
            )
        }
        Row(
            modifier = Modifier.padding(
                top = 8.dp
            )
        ) {
            Text(
                style = MaterialTheme.typography.body2,
                color = Color.White,
                text = stringResource(R.string.serving_glass_label, cocktail.glassType)
            )
        }
    }
}

@Composable
private fun cocktailProtoDetailsComponent(
    cocktailDetailsViewModel: ShakerCocktailDetailsViewModel,
    cocktail: ShakerCocktailModel,
    isFavourite: Boolean,
    onBackPressHandler: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(cocktail.photo)
                .crossfade(true)
                .build(),
            contentDescription = "",
            placeholder = painterResource(R.drawable.image_placeholder),
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
        )

        ShakerSurface(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 32.dp, start = 16.dp)
                .clickable {
                    cocktailDetailsViewModel.addCocktailToFavourites(cocktail)
                },
            shape = CircleShape,
            color = SemiTransparentBlack
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "",
                modifier = Modifier
                    .size(35.dp)
                    .padding(5.dp)
                    .clickable {
                        onBackPressHandler.invoke()
                    },
                tint = Color.White
            )
        }
        Text(
            text = cocktail.name ,
            style = MaterialTheme.typography.h4,
            color = Color.White,
            modifier = Modifier
                .padding(4.dp)
                .padding(start = 8.dp)
                .align(Alignment.BottomStart)
        )
        ShakerSurface(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .clickable {
                    cocktailDetailsViewModel.addCocktailToFavourites(cocktail)
                },
            shape = CircleShape,
            color = Color.Black
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .padding(
                        10.dp
                    ),
                tint = if(isFavourite) Color.Red else Color.White
            )
        }
    }
}

@Composable
private fun setStatusBarColor() {
    val view = LocalView.current

    if(!view.isInEditMode) {
        LaunchedEffect(key1 = true) {
            val window = (view.context as Activity).window
            window.statusBarColor = android.graphics.Color.TRANSPARENT
        }
    }
}

private fun getColumnVerticalAlignment(isDataLoading: Boolean): Arrangement.Vertical {
    return if(isDataLoading) Arrangement.Center else Arrangement.Top
}