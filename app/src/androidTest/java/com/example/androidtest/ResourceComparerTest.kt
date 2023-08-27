package com.example.androidtest

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
//import org.junit.Assert.* // Note that we do not use the assert that from the Junit instead we make use of the one from the truth library
import org.junit.Test

class ResourceComparerTest{
    /**
     * To create the instance of the class
     *  private val resourceComparer = ResourceComparer()
     *  But we will not use this method since we might have different functions so as to avoid errors
     */

    // Then to create a function that will work well for all, not withstanding
    private lateinit var resourceComparer : ResourceComparer // This allows this instance to be changed and recreated but it is a more flexible way


    @Before // This is Junit annotation that allows us to insert anything in any Test test case before it runs
    fun setUp(){
        resourceComparer = ResourceComparer() // Then we create the instance
    }

    @After  // This is where we destroy our objects or anything created. For example if we created an instnace of the room data base and then we want to destroy it at the end
    fun tearDown(){

    }




    @Test
    // Note that for any kind of test that is in the android test resource directory, we cannot name the functions in `` instead, we can just type the name without the braces or whatsoever
    fun stringResourceSameAsGivenString_returnsTrue(){
        // To then get the context since we are in the test component
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name, "AndroidTest")
        assertThat(result).isTrue()
    }

    @Test
    // Note that for any kind of test that is in the android test resource directory, we cannot name the functions in `` instead, we can just type the name without the braces or whatsoever
    fun stringResourceDifferentAsGivenString_returnsFalse(){
        // To then get the context since we are in the test component
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name, "Hello")
        assertThat(result).isFalse()
    }

    // Note that when we run this the emulator also starts since we are trying to make use of the context
}