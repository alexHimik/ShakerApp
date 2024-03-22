package com.shaker.domain.model

data class ShakerCocktailModel(
    val id: String,
    val name: String,
    val photo: String,
    val category: String,
    val type: String,
    val glassType: String,
    val preparingInstruction: String,
    val ingredients: List<ShakerCocktailIngredientModel> = emptyList()
)