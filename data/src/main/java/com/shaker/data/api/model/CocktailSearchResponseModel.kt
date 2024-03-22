package com.shaker.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CocktailSearchResponseModel(
    @SerialName("drinks") val cocktails: List<CocktailDetailsModel>?
)