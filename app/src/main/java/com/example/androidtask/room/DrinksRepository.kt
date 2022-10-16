package com.example.androidtask.room

import androidx.lifecycle.LiveData

class DrinksRepository(val dao:Dao) {

    val getAllDrinks: LiveData<MutableList<Entity>> = dao.getAllDrinks()

    suspend fun addDrinks(entity:Entity){

        dao.insertDrinks(entity)
    }

}