package com.shaker.data.repository.cocktail

import com.shaker.data.api.model.CocktailDetailsModel
import com.shaker.data.database.entity.CocktailCategoryEntity
import com.shaker.data.database.entity.CocktailEntity
import com.shaker.data.repository.cocktail.datasource.ShakerCocktailDataSource
import com.shaker.domain.either.Either
import com.shaker.domain.model.ShakerCocktailCategoryModel
import com.shaker.domain.model.ShakerCocktailIngredientModel
import com.shaker.domain.model.ShakerCocktailModel
import com.shaker.domain.repository.cocktail.ShakerCocktailRepository
import com.shaker.domain.result.Failure
import com.shaker.domain.storage.ShakerPreferenceStorage
import javax.inject.Inject

class ShakerCocktailRepositoryImpl @Inject constructor(
    private val cocktailRemoteSource: ShakerCocktailDataSource.Remote,
    private val cocktailLocalSource: ShakerCocktailDataSource.Local,
    private val preferenceStorage: ShakerPreferenceStorage
): ShakerCocktailRepository {

    private val DATA_UPDATE_INTERVAL = 24 * 60 * 60 * 1000 // 1 day

    override suspend fun getCocktailsCategories(): Either<Failure, List<ShakerCocktailCategoryModel>> {
        return if(shouldUpdateData(preferenceStorage.getCategoriesUpdateStamp())) {
            val remoteResponse = cocktailRemoteSource.getCocktailCategories()
            if(remoteResponse.isLeft) {
                Either.Left((remoteResponse as Either.Left).a)
            } else {
                val categories = (remoteResponse as Either.Right).b.map {
                    ShakerCocktailCategoryModel(it.categoryName)
                }
                cocktailLocalSource.saveCocktailCategories(categories.map {
                    CocktailCategoryEntity(null, it.categoryName)
                })
                preferenceStorage.setCategoriesUpdateStamp(System.currentTimeMillis())
                Either.Right(categories)
            }
        } else {
            val localData = cocktailLocalSource.getCocktailsCategories()
            Either.Right(localData.map { ShakerCocktailCategoryModel(it.categoryName) })
        }
    }

    override suspend fun getLastViewedCocktails(): Either<Failure, List<ShakerCocktailModel>> {
        return Either.Right(cocktailLocalSource.getLastViewedCocktails().map { ShakerCocktailModel(
            it.id,
            it.strDrink,
            it.strPhoto,
            it.strCategory,
            it.strType,
            it.strGlass,
            it.strInstructions
        ) })
    }

    override suspend fun getFavoriteCocktails(): Either<Failure, List<ShakerCocktailModel>> {
        return Either.Right(cocktailLocalSource.getFavoriteCocktails().map { ShakerCocktailModel(
            it.id,
            it.strDrink,
            it.strPhoto,
            it.strCategory,
            it.strType,
            it.strGlass,
            it.strInstructions
        ) })
    }

    override suspend fun searchCocktailByName(nameValue: String): Either<Failure, List<ShakerCocktailModel>> {
        val remoteResponse = cocktailRemoteSource.searchCocktailByName(nameValue)
        return if(remoteResponse.isLeft) {
            val failureData = (remoteResponse as Either.Left).a
            Either.Left(failureData)
        } else {
            val cocktailsData = (remoteResponse as Either.Right).b
            if(cocktailsData != null) {
                Either.Right(cocktailsData.map { ShakerCocktailModel(
                    it.idDrink.orEmpty(),
                    it.strDrink.orEmpty(),
                    it.strDrinkThumb.orEmpty(),
                    it.strCategory.orEmpty(),
                    it.strAlcoholic.orEmpty(),
                    it.strGlass.orEmpty(),
                    it.strInstructions.orEmpty(),
                    prepareCocktailIngredients(it)
                ) })
            } else {
                Either.Left(Failure.NoDataError())
            }

        }
    }

    override suspend fun addCocktailToFavorites(cocktail: ShakerCocktailModel): Either<Failure, Any> {
        cocktailLocalSource.addCocktailToFavorites(cocktail.id)
        return Either.Right(emptyList<Any>())
    }

    override suspend fun addCocktailToLastViewed(cocktail: ShakerCocktailModel): Either<Failure, Any> {
        val entityValue = CocktailEntity(
            cocktail.id,
            cocktail.name,
            cocktail.category,
            "N/A",
            cocktail.glassType,
            cocktail.preparingInstruction,
            cocktail.photo,
            cocktail.type,
            0
        )
        cocktailLocalSource.addCocktailToLastViewed(entityValue)
        return Either.Right(emptyList<Any>())
    }

    override suspend fun getCategoryCocktails(categoryName: String): Either<Failure, List<ShakerCocktailModel>> {
        val remoteResponse = cocktailRemoteSource.getCategoryCocktails(categoryName)
        return if(remoteResponse.isLeft) {
            val failureData = (remoteResponse as Either.Left).a
            Either.Left(failureData)
        } else {
            val cocktailsData = (remoteResponse as Either.Right).b
            Either.Right(cocktailsData.map { ShakerCocktailModel(
                it.idDrink.orEmpty(),
                it.strDrink.orEmpty(),
                it.strDrinkThumb.orEmpty(),
                it.strCategory.orEmpty(),
                it.strAlcoholic.orEmpty(),
                it.strGlass.orEmpty(),
                it.strInstructions.orEmpty(),
                prepareCocktailIngredients(it)
            ) })
        }
    }

    override suspend fun getCocktailsDetails(cocktailId: String): Either<Failure, ShakerCocktailModel> {
        return try {
            val cocktailData = cocktailLocalSource.getCocktailDetails(cocktailId)
            if(cocktailData != null) {
                Either.Right(ShakerCocktailModel(
                    cocktailData.id,
                    cocktailData.strDrink,
                    cocktailData.strPhoto,
                    cocktailData.strCategory,
                    cocktailData.strType,
                    cocktailData.strInstructions,
                    cocktailData.strGlass
                ))
            } else {
                val response = cocktailRemoteSource.getRandomCocktailDetails()
                return if(response.isRight) {
                    val cocktail = (response as Either.Right).b
                    Either.Right(ShakerCocktailModel(
                        cocktail.idDrink.orEmpty(),
                        cocktail.strDrink.orEmpty(),
                        cocktail.strDrinkThumb.orEmpty(),
                        cocktail.strCategory.orEmpty(),
                        cocktail.strAlcoholic.orEmpty(),
                        cocktail.strInstructions.orEmpty(),
                        cocktail.strGlass.orEmpty(),
                        prepareCocktailIngredients(cocktail)
                    ))
                } else {
                    response as Either.Left
                }
            }
        } catch (ex: Exception) {
            Either.Left(Failure.NoDataError())
        }
    }

    override suspend fun getRandomCocktailDetails(): Either<Failure, ShakerCocktailModel> {
        val response = cocktailRemoteSource.getRandomCocktailDetails()
        return if(response.isRight) {
            val cocktail = (response as Either.Right).b
            Either.Right(ShakerCocktailModel(
                cocktail.idDrink.orEmpty(),
                cocktail.strDrink.orEmpty(),
                cocktail.strDrinkThumb.orEmpty(),
                cocktail.strCategory.orEmpty(),
                cocktail.strAlcoholic.orEmpty(),
                cocktail.strInstructions.orEmpty(),
                cocktail.strGlass.orEmpty(),
                prepareCocktailIngredients(cocktail)
            ))
        } else {
            response as Either.Left
        }
    }

    private fun prepareCocktailIngredients(cocktail: CocktailDetailsModel): List<ShakerCocktailIngredientModel> {
        val ingredients = listOf(
            cocktail.strIngredient1, cocktail.strIngredient2, cocktail.strIngredient3,
            cocktail.strIngredient4, cocktail.strIngredient5, cocktail.strIngredient6,
            cocktail.strIngredient7, cocktail.strIngredient8, cocktail.strIngredient9,
            cocktail.strIngredient10, cocktail.strIngredient11, cocktail.strIngredient12,
            cocktail.strIngredient13, cocktail.strIngredient14, cocktail.strIngredient15
        )
        val ingredientMeasure = listOf(
            cocktail.strMeasure1, cocktail.strMeasure2, cocktail.strMeasure3,
            cocktail.strMeasure4, cocktail.strMeasure5, cocktail.strMeasure6,
            cocktail.strMeasure7, cocktail.strMeasure8, cocktail.strMeasure9,
            cocktail.strMeasure10, cocktail.strMeasure11, cocktail.strMeasure12,
            cocktail.strMeasure13, cocktail.strMeasure14, cocktail.strMeasure15
        )
        val shakerIngredients = ArrayList<ShakerCocktailIngredientModel>()
        ingredients.forEachIndexed { index, name ->
            shakerIngredients.add(ShakerCocktailIngredientModel(
                name.orEmpty(),
                ingredientMeasure[index].orEmpty()
            ))
        }
        return shakerIngredients
    }

    private fun shouldUpdateData(lastUpdateStamp: Long): Boolean {
        return lastUpdateStamp + DATA_UPDATE_INTERVAL < System.currentTimeMillis()
    }
}