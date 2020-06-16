package com.example.newsapp.utils

object Constants {
    /*API*/
    const val BASE_URL = "https://newsapi.org"
    const val END_POINT_TOP_HEADLINE = "/v2/top-headlines"
    const val END_POINT_EVERYTHING = "/v2/everything"

    const val API_KEY_TAG = "apiKey"
    const val API_KEY_VALUE = "9a8fe8bd5031444e91b5129ddbdf57ab"

    const val COUNTRY_TAG = "country"
    const val COUNTRY_VALUE = "us"

    /*others*/
    const val HTML_DATA_TAG = "html_data"

    /*db*/
    private val DATABASE_NAME = "news_app.db"
    private val DATABASE_VERSION = 1
}