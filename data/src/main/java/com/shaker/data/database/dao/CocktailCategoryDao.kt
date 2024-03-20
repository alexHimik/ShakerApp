package com.shaker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shaker.data.database.entity.CocktailCategoryEntity

@Dao
interface CocktailCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCategories(categories: List<CocktailCategoryEntity>)

    @Query("SELECT * FROM cocktail_category")
    fun getCategories(): List<CocktailCategoryEntity>

    @Query("DELETE FROM cocktail_category")
    fun clearCategories()
}