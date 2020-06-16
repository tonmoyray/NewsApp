package com.example.newsapp.di.module

import com.example.newsapp.data.Api
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.data.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesUserRepository(api: Api): NewsRepository {
        return NewsRepositoryImpl(api)
    }
}