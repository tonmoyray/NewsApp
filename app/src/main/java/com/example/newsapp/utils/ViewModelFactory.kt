package com.example.newsapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.data.local.ArticleDaoManager
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.view.MainViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor (private val newsRepository: NewsRepository,
                                            private  val articleDaoManager: ArticleDaoManager,
                                            private  val commonMethods: CommonMethods) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(newsRepository, articleDaoManager,commonMethods) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}