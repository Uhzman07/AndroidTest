package com.example.androidtest.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.*

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)


    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    // Note that we do not make use of the suspend function with the live data functions since the live data itself is already asynchronous


    @Query("SELECT * FROM shopping_items")
    fun observeAllShoppingItems() : LiveData<List<ShoppingItem>>


    @Query("SELECT SUM(price * amount) FROM shopping_items")
    fun observeTotalPrice() : LiveData<Float>

}