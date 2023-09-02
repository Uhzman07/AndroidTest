package com.example.androidtest

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class MainCoroutineRule(
    private val dispatcher : CoroutineDispatcher = TestCoroutineDispatcher() // And then we add the dispatchers there

) : TestWatcher(), TestCoroutineScope by TestCoroutineScope(dispatcher){ // Note that this entire class is supposed to be a test watcher

    // This is an overridden function that is used to tell the coroutine what to do at the beginning
    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(dispatcher) // Here we are placing the main dispatcher with our test dispatcher
    }


    // This is what we want to make happen when we are finished with it
    override fun finished(description: Description) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain() // Then we have to reset the dispatcher from main
    }

}