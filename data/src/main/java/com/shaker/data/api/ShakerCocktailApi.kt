package com.shaker.data.api

import com.shaker.data.api.model.CocktailCategoriesResponseModel
import com.shaker.data.api.model.CocktailSearchResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShakerCocktailApi {

    @GET("{api_key}/list.php?c=list")
    suspend fun getCocktailCategories(
        @Path("api_key") apiKey: String
    ): CocktailCategoriesResponseModel

    @GET("{api_key}/search.php")
    suspend fun searchCocktailByName(
        @Path("api_key") apiKey: String,
        @Query("s") nameStartWith: String
    ): CocktailSearchResponseModel

    @GET("{api_key}/filter.php")
    suspend fun getCategoryCocktails(
        @Path("api_key") apiKey: String,
        @Query("c") nameStartWith: String
    ): CocktailSearchResponseModel

    @GET("{api_key}/random.php")
    suspend fun getRandomCocktailData(
        @Path("api_key") apiKey: String
    ): CocktailSearchResponseModel

    @GET("{api_key}/lookup.php")
    suspend fun getCocktailData(
        @Path("api_key") apiKey: String,
        @Query("i") nameStartWith: String
    ): CocktailSearchResponseModel
}