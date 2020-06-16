package com.example.newsapp.di.component

import com.example.newsapp.di.module.AppModule
import com.example.newsapp.di.module.RepositoryModule
import com.example.newsapp.di.module.ViewModelModule
import com.example.newsapp.view.MainActivity
import com.example.newsapp.view.HomeFragment
import com.example.newsapp.view.NewsDetailsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(homeFragment: HomeFragment)
    fun inject(newsDetailsFragment: NewsDetailsFragment)
}