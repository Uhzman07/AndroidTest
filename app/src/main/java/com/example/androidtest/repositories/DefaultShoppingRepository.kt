package com.example.androidtest.repositories

import androidx.lifecycle.LiveData
import com.example.androidtest.data.local.ShoppingDao
import com.example.androidtest.data.local.ShoppingItem
import com.example.androidtest.data.remote.responses.ImageResponse
import com.example.androidtest.data.remote.responses.PixabayAPI
import com.example.androidtest.other.Resource
import retrofit2.Response
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao : ShoppingDao,
    private val pixabayAPI: PixabayAPI
): ShoppingRepository { // Note that this is inheriting from the simple shopping repository that we have

    // Note that all these functions are from the functions that we had created
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeAllTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try{
            val response = pixabayAPI.searchForImage(imageQuery)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it) // "it" here is the data of the response that is we are trying to return the data response to the user
                }?:Resource.error("An unknown error occurred", null) // Note that the null here means that there is no data allowed
            }else{
                Resource.error("An unknown error occurred",null)
            }

        } catch (e : Exception){
            Resource.error("Couldn't reach the server. Check your internet connection",null)
        }
    }




}