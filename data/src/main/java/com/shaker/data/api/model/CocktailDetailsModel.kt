package com.shaker.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CocktailDetailsModel(
    @SerialName("idDrink")
    var idDrink: String?,
    @SerialName("strDrink")
    var strDrink: String?,
    @SerialName("strDrinkAlternate")
    var strDrinkAlternate: String?,
    @SerialName("strTags")
    var strTags: String?,
    @SerialName("strVideo")
    var strVideo: String?,
    @SerialName("strCategory")
    var strCategory: String?,
    @SerialName("strIBA")
    var strIBA: String?,
    @SerialName("strAlcoholic")
    var strAlcoholic: String?,
    @SerialName("strGlass")
    var strGlass: String?,
    @SerialName("strInstructions")
    var strInstructions: String?,
    @SerialName("strInstructionsES")
    var strInstructionsES: String?,
    @SerialName("strInstructionsDE")
    var strInstructionsDE: String?,
    @SerialName("strInstructionsFR")
    var strInstructionsFR: String?,
    @SerialName("strInstructionsIT")
    var strInstructionsIT: String?,
    @SerialName("strInstructionsZH-HANS")
    var strInstructionsZHHANS: String?,
    @SerialName("strInstructionsZH-HANT")
    var strInstructionsZHHANT: String?,
    @SerialName("strDrinkThumb")
    var strDrinkThumb: String?,
    @SerialName("strIngredient1")
    var strIngredient1: String?,
    @SerialName("strIngredient2")
    var strIngredient2: String?,
    @SerialName("strIngredient3")
    var strIngredient3: String?,
    @SerialName("strIngredient4")
    var strIngredient4: String?,
    @SerialName("strIngredient5")
    var strIngredient5: String?,
    @SerialName("strIngredient6")
    var strIngredient6: String?,
    @SerialName("strIngredient7")
    var strIngredient7: String?,
    @SerialName("strIngredient8")
    var strIngredient8: String?,
    @SerialName("strIngredient9")
    var strIngredient9: String?,
    @SerialName("strIngredient10")
    var strIngredient10: String?,
    @SerialName("strIngredient11")
    var strIngredient11: String?,
    @SerialName("strIngredient12")
    var strIngredient12: String?,
    @SerialName("strIngredient13")
    var strIngredient13: String?,
    @SerialName("strIngredient14")
    var strIngredient14: String?,
    @SerialName("strIngredient15")
    var strIngredient15: String?,
    @SerialName("strMeasure1")
    var strMeasure1: String?,
    @SerialName("strMeasure2")
    var strMeasure2: String?,
    @SerialName("strMeasure3")
    var strMeasure3: String?,
    @SerialName("strMeasure4")
    var strMeasure4: String?,
    @SerialName("strMeasure5")
    var strMeasure5: String?,
    @SerialName("strMeasure6")
    var strMeasure6: String?,
    @SerialName("strMeasure7")
    var strMeasure7: String?,
    @SerialName("strMeasure8")
    var strMeasure8: String?,
    @SerialName("strMeasure9")
    var strMeasure9: String?,
    @SerialName("strMeasure10")
    var strMeasure10: String?,
    @SerialName("strMeasure11")
    var strMeasure11: String?,
    @SerialName("strMeasure12")
    var strMeasure12: String?,
    @SerialName("strMeasure13")
    var strMeasure13: String?,
    @SerialName("strMeasure14")
    var strMeasure14: String?,
    @SerialName("strMeasure15")
    var strMeasure15: String?,
    @SerialName("strImageSource")
    var strImageSource: String?,
    @SerialName("strImageAttribution")
    var strImageAttribution: String?,
    @SerialName("strCreativeCommonsConfirmed")
    var strCreativeCommonsConfirmed: String?,
    @SerialName("dateModified")
    var dateModified: String?
)