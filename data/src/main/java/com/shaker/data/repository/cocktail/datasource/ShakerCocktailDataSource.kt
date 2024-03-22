package com.shaker.data.repository.cocktail.datasource

import com.shaker.data.api.model.CocktailCategoryModel
import com.shaker.data.api.model.CocktailDetailsModel
import com.shaker.data.database.entity.CocktailCategoryEntity
import com.shaker.data.database.entity.CocktailEntity
import com.shaker.domain.either.Either
import com.shaker.domain.result.Failure

interface ShakerCocktailDataSource {

    interface Remote {
        suspend fun getCocktailCategories(): Either<Failure, List<CocktailCategoryModel>>

        suspend fun searchCocktailByName(nameValue: String): Either<Failure, List<CocktailDetailsModel>?>

        suspend fun getCategoryCocktails(categoryName: String): Either<Failure, List<CocktailDetailsModel>>

        suspend fun getRandomCocktailDetails(): Either<Failure, CocktailDetailsModel>

        suspend fun getCocktailDetails(cocktailId: String): Either<Failure, CocktailDetailsModel>
    }

    interface Local {
        suspend fun addCocktailToFavorites(cocktailId: String)

        suspend fun addCocktailToLastViewed(cocktail: CocktailEntity)

        suspend fun getFavoriteCocktails(): List<CocktailEntity>

        suspend fun getLastViewedCocktails(): List<CocktailEntity>

        suspend fun getCocktailsCategories(): List<CocktailCategoryEntity>

        suspend fun saveCocktailCategories(categories: List<CocktailCategoryEntity>)

        suspend fun getCocktailDetails(cocktailsId: String): CocktailEntity?
    }
}