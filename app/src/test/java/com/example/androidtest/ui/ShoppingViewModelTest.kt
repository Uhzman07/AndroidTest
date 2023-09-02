package com.example.androidtest.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.androidtest.MainCoroutineRule
import com.example.androidtest.getOrAwaitValueTest
import com.example.androidtest.other.Constants
import com.example.androidtest.other.Status
import com.example.androidtest.repositories.FakeShoppingRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
//import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ShoppingViewModelTest{


    /**
     * Note that the instantTaskExecutor rule is compulsory
     * It makes sure that everything runs in the main thread one after another
     */

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()




    // Note that we had to add the main coroutine file so that all the tests run in that main thread
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()



    private lateinit var viewModel: ShoppingViewModel

    @Before
    fun setup(){
        viewModel = ShoppingViewModel(FakeShoppingRepository()) // Note that this just imitates the real database structure. \
        // That is, this does not make use of the real data base but the things that we had created in the Fake repository
    }

    @Test
    fun `insert shopping item with empty field, returns error`(){
        viewModel.insertShoppingItem("name", "", "3.0")

        // To use this below, we have to copy the "LiveDataUtilAndroidTest" to the test package
        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        // Note that below, if we call the shopping item status initially, it returns the body/ content of the resource so we need to make a check or something
        // also note that the resource has many parts like status, data and message
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR) // Note that ".getContentIfNotHandled()" means that we do not want to get the data from it exactly
    }

    @Test
    fun `insert shopping item with too long name, returns error`(){
        // Here we want to test if the string input is greater than thw max length
        // Then we make use of the string builder to get various instances of string length so as not to hardcore a string of a particular length

        val string = buildString {
            for(i in 1..Constants.MAX_NAME_LENGTH){
                append(i)
            }
        }

        viewModel.insertShoppingItem(string, "5", "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long price, returns error`(){
        // Here we want to test if the string input is greater than thw max length
        // Then we make use of the string builder to get various instances of string length so as not to hardcore a string of a particular length

        val string = buildString {
            for(i in 1..Constants.MAX_PRICE_LENGTH){
                append(i)
            }
        }

        viewModel.insertShoppingItem("name", "5", string)

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too high amount, returns error`(){

        viewModel.insertShoppingItem("name", "99999999999", "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with valid input returns success`(){

        viewModel.insertShoppingItem("name", "5", "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest() // This is when we await the status value

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }



}