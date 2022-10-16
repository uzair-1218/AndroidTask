package com.example.androidtask.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDrinks(instance : Entity)


    @Query("select * from `Favourite Drinks`")
    fun getAllDrinks() : LiveData<MutableList<Entity>>

    @Query("select * from `Favourite Drinks`")
    fun getAllData() : MutableList<Entity>
}