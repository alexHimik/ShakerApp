package com.shaker.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CocktailCategoriesResponseModel(
    @SerialName("drinks") val categories: List<CocktailCategoryModel>
)