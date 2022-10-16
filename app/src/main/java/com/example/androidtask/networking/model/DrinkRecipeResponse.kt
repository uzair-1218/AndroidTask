package com.example.androidtask.networking.model

import com.google.gson.annotations.SerializedName

class DrinkRecipeResponse {
    @SerializedName("drinks" ) var drinks : ArrayList<Drinks> = arrayListOf()
}