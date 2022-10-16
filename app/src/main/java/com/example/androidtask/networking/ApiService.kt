package com.example.androidtask.networking

import com.example.androidtask.networking.model.DrinkRecipeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("json/v1/1/search.php")
    suspend fun getRecipeByName(@Query ("s") name:String) : DrinkRecipeResponse

    @GET("json/v1/1/search.php")
    suspend fun getRecipeByAlphabet(@Query("f") alphabet:String) : DrinkRecipeResponse
}