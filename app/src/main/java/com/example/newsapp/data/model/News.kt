package com.example.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class News (
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<Article>,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    var error: Boolean
)