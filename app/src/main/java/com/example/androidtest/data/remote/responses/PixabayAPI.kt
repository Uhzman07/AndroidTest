package com.example.androidtest.data.remote.responses

import com.example.androidtest.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI{

    @GET("/api/") // This is used to get the api using retrofit,  to specify that it's an HTTP GET request to the "/api/"
    suspend fun searchForImage(
        // Then to use the Queries
        @Query("q") searchQuery : String, // Note that "q" here is the search string for this query, just to say that it is representing the string to be passed
        @Query("key") apiKey: String = BuildConfig.API_KEY // Note that since we had hidden the api key using gradle.properties and the gradle.build file, we can then access the api key by the class gradle has generated for us
    ): Response<ImageResponse> // Note that response here is just like the Resource file but the "Response" is used to signify api responses



}


