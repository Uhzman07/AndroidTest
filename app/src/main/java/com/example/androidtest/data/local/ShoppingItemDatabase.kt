package com.example.androidtest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [ShoppingItem::class],
    version = 1,
    exportSchema = false
)
abstract class ShoppingItemDatabase : RoomDatabase(){

    // To then get the interface that we had created

    abstract fun shoppingDao() : ShoppingDao

}

// To create the test for the database since it uses context and all, we have to create a new package in the android test directory and then we create a new class for it
