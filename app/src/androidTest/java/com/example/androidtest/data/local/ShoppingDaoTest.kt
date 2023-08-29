package com.example.androidtest.data.local

// Note
// To create the test for the database since it uses context and all, we have to create a new package in the android test directory and then we create a new class for it

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.filters.MediumTest
import androidx.test.filters.SmallTest
import com.example.androidtest.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.io.encoding.ExperimentalEncodingApi

@ExperimentalCoroutinesApi // This is also needed when working with live data
@RunWith(AndroidJUnit4::class) // This is to tell JVM to allow anything that is inside this test class to run on the emulator and then to tell Junit that this is an instrumented test
@SmallTest // This is to tell JVM that we are writing unit tests in here
//@MediumTest // This is to tell JVM that we are writing integrated tests in here
//@LargeTest // This is to tell JVM that we are writing UI test in here
class ShoppingDaoTest {

    // Note that we always have to set this rule so as to avoid any kind of error
    @get:Rule
    var instanceTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingItemDatabase // Note that we have to initialize like this so that we can have one instance for all
    private lateinit var dao: ShoppingDao


    @Before // This is the annotation that we use for something needed at the beginning
    fun setup() {
        // This "inMemoryDatabaseBuilder()" is used to tell JVM that we are not creating a real data base and then we are trying to store it in the RAM instead
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build() // Note that we are allowing the database to run in main threads because we might have different threads that may be interrupting each other so we do not want to have an issue with that

        // Then for the interface
        dao = database.shoppingDao()
    }

    @After // This is when we want to do something afterwards
    fun teardown(){
        database.close()
    }

    @Test
    fun insertShoppingItem() =
        // Note that run blocking is a way to execute the coroutine in the main thread
        // This will block the main thread with suspend calls
        // We would have just written runBlocking{} but coroutine provides runBlockingTest{} for the tests
        // The runBlockingTest is very helpful in such a way that it helps us to skip through any delay that could have been in any of the functions

        runBlockingTest {
            val shoppingItem = ShoppingItem("name", 1, 1f, "url", id =1)
            dao.insertShoppingItem(shoppingItem)


            // Then to make use of anything that involves live data
            // Note that since live data is asynchronous, we always have to add the ".getOrAwaitValue()" function so as to make it work
            val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue() // Note that we have to have added the external file

            assertThat(allShoppingItems).contains(shoppingItem) // This is the used to check if it is contained

        }

    // Note that in order to make use of any live data, we have to copy a file to our android test package and then paste it in our root package which is "androidtest" for me initially


    @Test
    fun deleteShoppingItems() = runBlockingTest{
        val shoppingItem = ShoppingItem("name", 1, 1f, "url",id=1)

        // Then to test if we are able to delete
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()

        // This is then used to check if shopping items entity does not contain that particular shopping item
        assertThat(allShoppingItems).doesNotContain(shoppingItem)


    }

    @Test
    fun observeTotalPriceSum() = runBlockingTest {
        val shoppingItem1 = ShoppingItem("name", 2,10f, "url",id=1)
        val shoppingItem2 = ShoppingItem("name", 4, 5.5f, "url",id=2)
        val shoppingItem3 = ShoppingItem("name", 0, 100f, "url",id=3)

        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        val totalPriceSum = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalPriceSum).isEqualTo(2*10f + 4 *5.5f + 0)
    }




}

/**
 * Note that for new android we also have to add these to our manifest file
 * <activity
android:name="androidx.test.core.app.InstrumentationActivityInvoker$BootstrapActivity"
android:exported="true">
</activity>
<activity
android:name="androidx.test.core.app.InstrumentationActivityInvoker$EmptyActivity"
android:exported="true">
</activity>
<activity
android:name="androidx.test.core.app.InstrumentationActivityInvoker$EmptyFloatingActivity"
android:exported="true">
</activity>

 */