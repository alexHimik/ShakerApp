package com.shaker.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktails")
data class CocktailEntity(
    @PrimaryKey
    @ColumnInfo(name = "cocktail_id")
    var id: String,
    @ColumnInfo(name = "cocktail_name")
    var strDrink: String,
    @ColumnInfo(name = "cocktail_category")
    var strCategory: String,
    @ColumnInfo(name = "cocktail_abv")
    var strIBA: String,
    @ColumnInfo(name = "cocktail_glass")
    var strGlass: String,
    @ColumnInfo(name = "cocktail_prep_instruction")
    var strInstructions: String?,
)