package com.example.androidtest.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingItem (
    var name : String,
    var amount : Int,
    var price: Float,
    var imageUrl: String,

    @PrimaryKey(autoGenerate = true) // This means that we want to auto generate the id for the database

    val id: Int? = null
)
