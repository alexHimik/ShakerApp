package com.shaker.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shaker.data.database.entity.CocktailEntity

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCocktailToViewed(cocktailEntity: CocktailEntity)

    @Query("UPDATE cocktails SET is_favourite = 1 WHERE cocktail_id == :cocktailId")
    fun addCocktailToFavourites(cocktailId: String)

    @Query("UPDATE cocktails SET is_favourite = 0 WHERE cocktail_id == :cocktailId")
    fun removeCocktailFromFavourites(cocktailId: String)

    @Query("SELECT * FROM cocktails")
    fun getLastViewedCocktails(): List<CocktailEntity>

    @Query("SELECT * FROM cocktails WHERE is_favourite == 1")
    fun getFavouriteCocktails(): List<CocktailEntity>

    @Query("SELECT * FROM cocktails WHERE cocktail_id == :id")
    fun getCocktailById(id: String): CocktailEntity?

    @Query("DELETE FROM cocktails")
    fun clearData()
}