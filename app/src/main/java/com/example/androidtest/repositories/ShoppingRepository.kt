package com.example.androidtest.repositories

import androidx.lifecycle.LiveData
import com.example.androidtest.data.local.ShoppingItem
import com.example.androidtest.data.remote.responses.ImageResponse
import com.example.androidtest.other.Resource
import retrofit2.Response

// Note that this is the interface

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeAllTotalPrice() : LiveData<Float>

    suspend fun searchForImage(imageQuery : String) : Resource<ImageResponse>
}