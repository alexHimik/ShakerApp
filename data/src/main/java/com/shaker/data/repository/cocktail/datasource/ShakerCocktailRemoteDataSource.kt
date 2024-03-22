package com.shaker.data.repository.cocktail.datasource

import com.shaker.data.api.ShakerCocktailApi
import com.shaker.data.api.model.CocktailCategoryModel
import com.shaker.data.api.model.CocktailDetailsModel
import com.shaker.domain.either.Either
import com.shaker.domain.result.Failure
import com.shaker.domain.storage.ShakerCredentialStorage
import java.lang.Exception
import javax.inject.Inject

class ShakerCocktailRemoteDataSource @Inject constructor(
    private val shakerApi: ShakerCocktailApi,
    private val credentialStorage: ShakerCredentialStorage
) : ShakerCocktailDataSource.Remote {

    override suspend fun getCocktailCategories(): Either<Failure, List<CocktailCategoryModel>> {
       return try {
           Either.Right(shakerApi.getCocktailCategories(
               credentialStorage.provideApiCredentials()
           ).categories)
       } catch (ex: Exception) {
           Either.Left(Failure.NetworkConnection())
       }
    }

    override suspend fun searchCocktailByName(nameValue: String): Either<Failure, List<CocktailDetailsModel>?> {
        return try {
            Either.Right(shakerApi.searchCocktailByName(credentialStorage.provideApiCredentials(),
                nameValue).cocktails)
        } catch (ex: Exception) {
            Either.Left(Failure.NetworkConnection())
        }
    }

    override suspend fun getCategoryCocktails(categoryName: String): Either<Failure, List<CocktailDetailsModel>> {
        return try {
            Either.Right(shakerApi.getCategoryCocktails(credentialStorage.provideApiCredentials(),
                categoryName).cocktails.orEmpty())
        } catch (ex: Exception) {
            Either.Left(Failure.NetworkConnection())
        }
    }

    override suspend fun getRandomCocktailDetails(): Either<Failure, CocktailDetailsModel> {
        return try {
            val response = shakerApi.getRandomCocktailData(credentialStorage.provideApiCredentials())
            if(response.cocktails?.isNotEmpty() == true)Either.Right(response.cocktails[0]) else Either.Left(Failure.NoDataError())
        } catch (ex: Exception) {
            Either.Left(Failure.NetworkConnection())
        }
    }

    override suspend fun getCocktailDetails(cocktailId: String): Either<Failure, CocktailDetailsModel> {
        return try {
            val response = shakerApi.getCocktailData(credentialStorage.provideApiCredentials(), cocktailId)
            if(response.cocktails?.isNotEmpty() == true)Either.Right(response.cocktails[0]) else Either.Left(Failure.NoDataError())
        } catch (ex: Exception) {
            Either.Left(Failure.NetworkConnection())
        }
    }
}