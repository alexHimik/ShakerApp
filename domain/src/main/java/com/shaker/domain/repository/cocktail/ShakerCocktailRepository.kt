package com.shaker.domain.repository.cocktail

import com.shaker.domain.either.Either
import com.shaker.domain.model.ShakerCocktailCategoryModel
import com.shaker.domain.model.ShakerCocktailModel
import com.shaker.domain.result.Failure
import kotlinx.coroutines.flow.Flow

interface ShakerCocktailRepository {

    suspend fun getCocktailsCategories(): Either<Failure, List<ShakerCocktailCategoryModel>>

    suspend fun getLastViewedCocktails(): Either<Failure, List<ShakerCocktailModel>>

    suspend fun getFavoriteCocktails(): Either<Failure, List<ShakerCocktailModel>>

    fun searchCocktailByName(nameValue: String): Flow<Either<Failure, List<ShakerCocktailModel>>>

    suspend fun addCocktailToFavorites(cocktail: ShakerCocktailModel): Either<Failure, Any>

    suspend fun addCocktailToLastViewed(cocktail: ShakerCocktailModel): Either<Failure, Any>
}