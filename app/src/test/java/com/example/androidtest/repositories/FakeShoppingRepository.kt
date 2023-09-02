package com.example.androidtest.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidtest.data.local.ShoppingItem
import com.example.androidtest.data.remote.responses.ImageResponse
import com.example.androidtest.other.Resource

class FakeShoppingRepository : ShoppingRepository{

    // Note that this fake shopping repository is designed to just act like the default shopping repository but it is prepared for a time whereby we have no internet connection or whatsoever so we should try to simulate in a way that it won't require the internet

    private val shoppingItems = mutableListOf<ShoppingItem>()

    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value:Boolean){
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData(){
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }
    private fun getTotalPrice() : Float{
        return shoppingItems.sumByDouble { it.price.toDouble()}.toFloat() // This is used to calculate the sum of all the prices in double and then convert it to float later on
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {

        shoppingItems.remove(shoppingItem)
        refreshLiveData()


    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return observableShoppingItems

    }

    override fun observeAllTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return if(shouldReturnNetworkError){
            Resource.error("Error",null)
        } else{
            Resource.success(ImageResponse(listOf(),0,0))
        }
    }
}