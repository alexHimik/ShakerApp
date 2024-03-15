package com.shaker.data.repository.cocktail

import com.shaker.data.repository.cocktail.datasource.ShakerCocktailDataSource
import com.shaker.domain.either.Either
import com.shaker.domain.model.ShakerCocktailCategoryModel
import com.shaker.domain.model.ShakerCocktailModel
import com.shaker.domain.repository.cocktail.ShakerCocktailRepository
import com.shaker.domain.result.Failure
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShakerCocktailRepositoryImpl @Inject constructor(
    private val cocktailRemoteSource: ShakerCocktailDataSource.Remote,
    private val cocktailLocalSource: ShakerCocktailDataSource.Local
): ShakerCocktailRepository {

    override suspend fun getCocktailsCategories(): Either<Failure, List<ShakerCocktailCategoryModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getLastViewedCocktails(): Either<Failure, List<ShakerCocktailModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteCocktails(): Either<Failure, List<ShakerCocktailModel>> {
        TODO("Not yet implemented")
    }

    override fun searchCocktailByName(nameValue: String): Flow<Either<Failure, List<ShakerCocktailModel>>> {
        TODO("Not yet implemented")
    }

    override suspend fun addCocktailToFavorites(cocktail: ShakerCocktailModel): Either<Failure, Any> {
        TODO("Not yet implemented")
    }

    override suspend fun addCocktailToLastViewed(cocktail: ShakerCocktailModel): Either<Failure, Any> {
        TODO("Not yet implemented")
    }
}