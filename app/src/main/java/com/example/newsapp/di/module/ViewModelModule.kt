package com.example.newsapp.di.module

import com.example.newsapp.data.local.ArticleDaoManager
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.utils.CommonMethods
import com.example.newsapp.utils.ViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun providesMainViewModelFactory(newsRepository: NewsRepository, articleDaoManager: ArticleDaoManager, commonMethods: CommonMethods): ViewModelFactory {
        return ViewModelFactory(newsRepository,articleDaoManager, commonMethods)
    }
}