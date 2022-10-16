package com.example.androidtask.room

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favourite Drinks")
data class Entity(

    var drink_name: String,

    var drink_description: String,

    var drink_alcoholic: String,
    var drink_image:Bitmap

){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}