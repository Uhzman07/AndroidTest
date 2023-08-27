package com.example.androidtest

import android.content.Context

class ResourceComparer {

    fun isEqual(context: Context, resId:Int, string:String) : Boolean{
        return context.getString(resId) == string
    }

    // Then we go to the name of the class, object or anything created and then we right click it and then we click on "generate" then "test" and then since the
    // Function here uses a context then we should make use of android test instead
}