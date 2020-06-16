package com.example.newsapp.view


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.data.local.ArticleDaoManager
import com.example.newsapp.data.local.ArticleDatabaseModel
import com.example.newsapp.data.model.Article
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.utils.CommonMethods
import com.example.newsapp.utils.Constants

class MainViewModel(private val newsRepository: NewsRepository,
                    private  val articleDaoManager: ArticleDaoManager,
                    private val commonMethods: CommonMethods)
    : ViewModel() {

    val mutableArticleList = MutableLiveData<List<ArticleDatabaseModel>>()
    val error = MutableLiveData<Boolean>()
    private var TAG : String  = MainViewModel::class.java.simpleName

    init {
        commonMethods.printLog(TAG,"MainViewModel -> init" )
       fetchLatestTopHeadlines(Constants.API_KEY_VALUE, Constants.COUNTRY_VALUE)
    }


    fun fetchLatestTopHeadlines(apiKey: String, country: String) {
        newsRepository.getTopHeadlines(apiKey,country,
            {
                saveArticlesToLocalDB(it.articles)
            },
            {
                mutableArticleList.value = articleDaoManager.getAllArticles()
            }
        )
    }

    fun saveArticlesToLocalDB(articles : List<Article>){
        articles.forEach{
            var articleDatabaseModel = ArticleDatabaseModel()
            articleDatabaseModel.title = it.title
            articleDatabaseModel.date = it.publishedAt
            articleDatabaseModel.imageUrl = it.urlToImage
            articleDatabaseModel.webUrl = it.url

            articleDaoManager.create(articleDatabaseModel)
        }
        mutableArticleList.value = articleDaoManager.getAllArticles()
    }

}