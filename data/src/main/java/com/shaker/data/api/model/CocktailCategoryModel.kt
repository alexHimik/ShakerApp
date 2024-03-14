package com.shaker.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CocktailCategoryModel(
    @SerialName("strCategory") val categoryName: String
)