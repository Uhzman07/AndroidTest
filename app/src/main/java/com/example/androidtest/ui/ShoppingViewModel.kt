package com.example.androidtest.ui

//import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.shoppinglisttestingyt.other.Event
import com.example.androidtest.data.local.ShoppingItem
import com.example.androidtest.data.remote.responses.ImageResponse
import com.example.androidtest.other.Constants
import com.example.androidtest.other.Resource
import com.example.androidtest.repositories.ShoppingRepository
import kotlinx.coroutines.launch
import retrofit2.http.Query
import java.lang.Exception
import javax.inject.Inject

class ShoppingViewModel @Inject constructor(
    private val repository: ShoppingRepository // Note that both the fake and default shopping repositories can have access through this

)  : ViewModel() {

    val shoppingItems = repository.observeAllShoppingItems()

    val totalPrice = repository.observeAllTotalPrice()

    // The to set our image
    // Mote that it is private so that it can only be accessed by the fragments
    // Also note that "Event" here is the file that was copied
    // Note that the Event class is useful when selecting something like this on the internet
    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    // Then to create a variable for the live data (images) that cannot be changed
    val images : LiveData<Event<Resource<ImageResponse>>> = _images

    private val _curImageUrl = MutableLiveData<String>()
    val curImageUrl : LiveData<String> = _curImageUrl

    private val _insertShoppingItemsStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus : LiveData<Event<Resource<ShoppingItem>>> = _insertShoppingItemsStatus

    fun setCurImageUrl(url:String){
        _curImageUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

    fun insertShoppingItemToDb(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name: String , amountString: String, priceString: String){
        if(name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()) {
            _insertShoppingItemsStatus.postValue(Event(Resource.error("The fields must not bswe empty", null)))
            return // This is to return out of this function
        }

        if (name.length > Constants.MAX_NAME_LENGTH){
            _insertShoppingItemsStatus.postValue(Event(Resource.error("The name of the item must not "
            + "exceed ${Constants.MAX_NAME_LENGTH} characters", null)))
            return // Return to exit the function immediately
        }
        if (priceString.length > Constants.MAX_PRICE_LENGTH){
            _insertShoppingItemsStatus.postValue(Event(Resource.error("The price of the item must not "
                    + "exceed ${Constants.MAX_PRICE_LENGTH} characters", null)))
            return // Note that return helps us to be able return out of a function so as to prevent other things from entering them
        }
        // Then to check the amount
        val amount = try{
            amountString.toInt()
        } catch (e:Exception){
            _insertShoppingItemsStatus.postValue(Event(Resource.error("Please enter a valid amount", null)))

            return
        }
        // Then for the case where by there is no kind of error
        val shoppingItem = ShoppingItem(name, amount, priceString.toFloat(), _curImageUrl.value?:"")
        insertShoppingItemToDb(shoppingItem)
        setCurImageUrl("") // The we have to return this back to an empty string for the url
        // Then since we are sure that the right things must have been done already
        _insertShoppingItemsStatus.postValue(Event(Resource.success(shoppingItem))) // Then we post the shoppingItem

    }

    // This is to search for an image through the api
    fun searchForImage(imageQuery: String){
        if(imageQuery.isEmpty()){
            return
        }
        // Note that we are making use of .value here and not postValue because we want all our live data observer to know about this particular one
        _images.value = Event(Resource.loading(null)) // This is to set it as a loading resource
        viewModelScope.launch {
            val response = repository.searchForImage(imageQuery)
            _images.value = Event(response)
        }

    }
}