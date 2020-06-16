package com.example.newsapp.data.repository

import com.example.newsapp.data.model.News

interface NewsRepository {
    fun getTopHeadlines(username: String, country: String, onSuccess: (news: News) -> Unit, onFailure: (t: Throwable) -> Unit)
}