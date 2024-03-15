package com.shaker.data.repository.cocktail.datasource

import com.shaker.data.database.ShakerAppDatabase
import com.shaker.data.database.dao.CocktailCategoryDao
import com.shaker.data.database.dao.CocktailDao
import com.shaker.data.database.entity.CocktailCategoryEntity
import com.shaker.data.database.entity.CocktailEntity
import javax.inject.Inject

class ShakerCocktailLocalDataSource @Inject constructor(
    private val addDatabase: ShakerAppDatabase,
    private val categoryDao: CocktailCategoryDao,
    private val cocktailDao: CocktailDao
): ShakerCocktailDataSource.Local {

    override suspend fun addCocktailToFavorites(cocktail: CocktailEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun addCocktailToLastViewed(cocktail: CocktailEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteCocktails(): List<CocktailEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getLastViewedCocktails(): List<CocktailEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getCocktailsCategories(): List<CocktailCategoryEntity> {
        TODO("Not yet implemented")
    }
}