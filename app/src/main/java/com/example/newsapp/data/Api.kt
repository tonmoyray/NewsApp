package com.example.newsapp.data


import com.example.newsapp.data.model.News
import com.example.newsapp.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET(Constants.END_POINT_TOP_HEADLINE)
    fun getUser(@Query(Constants.API_KEY_TAG) apiKey: String,
                @Query(Constants.COUNTRY_TAG) country: String): Call<News>
}