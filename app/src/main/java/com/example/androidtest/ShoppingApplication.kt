package com.example.androidtest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ShoppingApplication : Application() // We must add this to our manifest file