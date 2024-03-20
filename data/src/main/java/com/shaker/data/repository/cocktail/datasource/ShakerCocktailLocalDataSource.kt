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

    override suspend fun addCocktailToFavorites(cocktailId: String) {
        cocktailDao.addCocktailToFavourites(cocktailId)
    }

    override suspend fun addCocktailToLastViewed(cocktail: CocktailEntity) {
        cocktailDao.addCocktailToViewed(cocktail)
    }

    override suspend fun getFavoriteCocktails(): List<CocktailEntity> {
        return cocktailDao.getFavouriteCocktails()
    }

    override suspend fun getLastViewedCocktails(): List<CocktailEntity> {
        return cocktailDao.getLastViewedCocktails()
    }

    override suspend fun getCocktailsCategories(): List<CocktailCategoryEntity> {
        return categoryDao.getCategories()
    }

    override suspend fun saveCocktailCategories(categories: List<CocktailCategoryEntity>) {
        categoryDao.clearCategories()
        categoryDao.saveCategories(categories)
    }
}