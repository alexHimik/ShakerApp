package com.shaker.app.presentation.ui.screen.search

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.shaker.app.R
import com.shaker.app.presentation.ui.components.ShakerCard
import com.shaker.app.presentation.ui.components.ShakerImage
import com.shaker.app.presentation.ui.components.VerticalGrid
import com.shaker.app.presentation.ui.theme.ShakerTheme
import com.shaker.domain.model.ShakerCocktailModel
import kotlin.math.max

@Composable
fun FoundCocktailsGridList(
    cocktails: List<ShakerCocktailModel>,
    onCocktailClick: (ShakerCocktailModel) -> Unit
) {
    LazyColumn {
        itemsIndexed(cocktails) {index: Int, item: ShakerCocktailModel ->
            ShakerCard {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.h5,
                    color = ShakerTheme.colors.textPrimary,
                    modifier = Modifier
                        .heightIn(min = 48.dp)
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .wrapContentHeight()
                )
            }
        }
    }
}

@Composable
fun NoResults(
    query: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize()
            .padding(24.dp)
    ) {
        Image(
            painterResource(R.drawable.cocktail_glasses_empty),
            contentDescription = null
        )
        Spacer(Modifier.height(24.dp))
        val emptyScreenText = if(query.isNotEmpty()) {
            stringResource(R.string.search_no_matches, query)
        } else {
            stringResource(R.string.no_viewed_results_yet)
        }
        Text(
            text = emptyScreenText,
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.search_no_matches_retry),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CocktailsGridList(
    cocktails: List<ShakerCocktailModel>,
    onCocktailClick: (ShakerCocktailModel) -> Unit
) {
    LazyColumn {
        itemsIndexed(cocktails) {index: Int, item: ShakerCocktailModel ->
            CocktailsGridBlock(item, index)
        }
    }
}

@Composable
private fun CocktailsGridBlock(
    cocktail: ShakerCocktailModel,
    index: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = cocktail.name,
            style = MaterialTheme.typography.h5,
            color = ShakerTheme.colors.textPrimary,
            modifier = Modifier
                .heightIn(min = 48.dp)
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .wrapContentHeight()
        )
        VerticalGrid {
            val gradient = when (index % 2) {
                0 -> ShakerTheme.colors.gradient2_2
                else -> ShakerTheme.colors.gradient2_3
            }

        }
        Spacer(Modifier.height(4.dp))
    }
}

@Composable
private fun CocktailInfoItem(
    cocktail: ShakerCocktailModel,
    gradient: List<Color>,
    modifier: Modifier = Modifier,
    onCocktailClick: (ShakerCocktailModel) -> Unit
) {
    Layout(
        modifier = modifier
            .aspectRatio(1.45f)
            .shadow(elevation = 3.dp, shape = CategoryShape)
            .clip(CategoryShape)
            .background(Brush.horizontalGradient(gradient))
            .clickable { onCocktailClick.invoke(cocktail) },
        content = {
            Text(
                text = cocktail.name,
                style = MaterialTheme.typography.subtitle1,
                color = ShakerTheme.colors.textSecondary,
                modifier = Modifier
                    .padding(4.dp)
                    .padding(start = 8.dp)
            )
            ShakerImage(
                imageUrl = cocktail.photo,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    ) { measurables, constraints ->
        // Text given a set proportion of width (which is determined by the aspect ratio)
        val textWidth = (constraints.maxWidth * CategoryTextProportion).toInt()
        val textPlaceable = measurables[0].measure(Constraints.fixedWidth(textWidth))

        // Image is sized to the larger of height of item, or a minimum value
        // i.e. may appear larger than item (but clipped to the item bounds)
        val imageSize = max(MinImageSize.roundToPx(), constraints.maxHeight)
        val imagePlaceable = measurables[1].measure(Constraints.fixed(imageSize, imageSize))
        layout(
            width = constraints.maxWidth,
            height = constraints.minHeight
        ) {
            textPlaceable.placeRelative(
                x = 0,
                y = (constraints.maxHeight - textPlaceable.height) / 2 // centered
            )
            imagePlaceable.placeRelative(
                // image is placed to end of text i.e. will overflow to the end (but be clipped)
                x = textWidth,
                y = (constraints.maxHeight - imagePlaceable.height) / 2 // centered
            )
        }
    }
}

private val MinImageSize = 134.dp
private val CategoryShape = RoundedCornerShape(10.dp)
private const val CategoryTextProportion = 0.55f

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
private fun SearchCategoryPreview() {
    ShakerTheme {
        CocktailInfoItem(
            cocktail = ShakerCocktailModel(
                id = "",
                name = "Pinna Colladda",
                photo = "https://www.thecocktaildb.com//images//media//drink//w64lqm1504888810.jpg",
                category = "",
                type = "",
                glassType = ""
            ),
            gradient = ShakerTheme.colors.gradient3_2,
            onCocktailClick = {}
        )
    }
}