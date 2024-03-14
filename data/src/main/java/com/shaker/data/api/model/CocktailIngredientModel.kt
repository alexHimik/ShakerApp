package com.shaker.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CocktailIngredientModel(
    @SerialName("strIngredient1")
    val ingredientName: String
)