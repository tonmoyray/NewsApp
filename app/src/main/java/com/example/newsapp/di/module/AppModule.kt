package com.example.newsapp.di.module

import android.content.Context
import com.example.newsapp.BuildConfig
import com.example.newsapp.data.Api
import com.example.newsapp.data.local.ArticleDaoManager
import com.example.newsapp.utils.CommonMethods
import com.example.newsapp.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule (context: Context){

    private var context = context

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        interceptor.apply {
            if(BuildConfig.DEBUG){
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            }else{
                interceptor.level = HttpLoggingInterceptor.Level.NONE
            }
        }
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun providesArticleDaoManager(commonMethods: CommonMethods): ArticleDaoManager{
        return ArticleDaoManager(context, commonMethods)
    }


    @Provides
    @Singleton
    fun provideCommonMethods(): CommonMethods{
        return CommonMethods(context)
    }
}