package com.example.newsapp.data.repository

import com.example.newsapp.data.Api
import com.example.newsapp.data.model.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepositoryImpl(private val api: Api) : NewsRepository {


    override fun getTopHeadlines(username: String, country: String, onSuccess: (news: News) -> Unit, onFailure: (t: Throwable) -> Unit) {

        api.getUser(username,country)
            .enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                response.body()?.let { user ->
                    onSuccess.invoke(user)
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                onFailure.invoke(t)
            }
        })
    }


}