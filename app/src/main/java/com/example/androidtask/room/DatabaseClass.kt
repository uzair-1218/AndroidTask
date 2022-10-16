package com.example.androidtask.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Entity::class],exportSchema = false, version = 1)
@TypeConverters(Converter::class)
abstract class DatabaseClass : RoomDatabase() {

    abstract fun drinkdao() :Dao

    companion object{
        @Volatile
        private var instance: DatabaseClass? = null

        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            DatabaseClass::class.java, "Drinks.db")
            .allowMainThreadQueries().fallbackToDestructiveMigration()
            .build()
    }
}