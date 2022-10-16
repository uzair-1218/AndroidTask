package com.example.androidtask.fragments.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.androidtask.networking.RetrofitBuilder
import com.example.androidtask.networking.model.DrinkRecipeResponse
import com.example.androidtask.room.DatabaseClass
import com.example.androidtask.room.DrinksRepository
import com.example.androidtask.room.Entity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UniversalViewModel(application:Application):AndroidViewModel(application) {

     val drinks_get_by_name : MutableLiveData<DrinkRecipeResponse> = MutableLiveData()
    val drinks_get_by_alphabet : MutableLiveData<DrinkRecipeResponse> = MutableLiveData()
     val getAllData: LiveData<MutableList<Entity>>
    private val repository : DrinksRepository

    init {

        val drinkDao = DatabaseClass(application).drinkdao()
        repository = DrinksRepository(drinkDao)
        getAllData = repository.getAllDrinks

    }


    fun getAllDrinksRecipes(name:String){

    viewModelScope.launch {

        val response = RetrofitBuilder.api.getRecipeByName(name)
        drinks_get_by_name.postValue(response)
    }
    }

    fun getDrinksByAlphabet(alphabet:String){


        viewModelScope.launch {

            val response = RetrofitBuilder.api.getRecipeByAlphabet(alphabet)
            drinks_get_by_alphabet.postValue(response)
        }
    }

    fun  addDrinks(entity: Entity){

        viewModelScope.launch(Dispatchers.IO) {

            repository.addDrinks(entity)
        }
    }


}