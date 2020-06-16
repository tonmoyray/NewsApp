package com.example.newsapp

import android.app.Application
import com.example.newsapp.di.component.AppComponent
import com.example.newsapp.di.component.DaggerAppComponent
import com.example.newsapp.di.module.AppModule
import com.facebook.stetho.Stetho

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
            Stetho.initializeWithDefaults(this);
        }

        component = DaggerAppComponent
            .builder()
            .appModule( AppModule(applicationContext))
            .build()
    }
}
lateinit var component: AppComponent