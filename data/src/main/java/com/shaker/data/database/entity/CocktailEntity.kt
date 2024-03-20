package com.shaker.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktails")
data class CocktailEntity(
    @PrimaryKey
    @ColumnInfo(name = "cocktail_id")
    val id: String,
    @ColumnInfo(name = "cocktail_name")
    val strDrink: String,
    @ColumnInfo(name = "cocktail_category")
    val strCategory: String,
    @ColumnInfo(name = "cocktail_abv")
    val strIBA: String,
    @ColumnInfo(name = "cocktail_glass")
    val strGlass: String,
    @ColumnInfo(name = "cocktail_prep_instruction")
    val strInstructions: String,
    @ColumnInfo(name = "cocktail_photo")
    val strPhoto: String,
    @ColumnInfo(name = "cocktail_type")
    val strType: String,
    @ColumnInfo(name = "is_favourite")
    val favourite: Int
)