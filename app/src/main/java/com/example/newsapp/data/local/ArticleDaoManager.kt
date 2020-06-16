package com.example.newsapp.data.local

import android.content.Context
import android.util.Log
import com.example.newsapp.utils.CommonMethods
import com.j256.ormlite.cipher.android.apptools.OpenHelperManager
import com.j256.ormlite.stmt.PreparedQuery
import com.j256.ormlite.stmt.QueryBuilder
import java.sql.SQLException

class ArticleDaoManager (context: Context, commonMethods: CommonMethods) {

    val databaseHelper = OpenHelperManager.getHelper(
            context,
            DatabaseHelper::class.java
        )
    val articleDAO = databaseHelper.getArticleDao()!!


    fun create(articleDataModelObj: ArticleDatabaseModel): Int {
        try {
            if(!isDuplicate(articleDataModelObj)){
                return articleDAO.create(articleDataModelObj)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return 0
    }


    fun getAllArticles(): List<ArticleDatabaseModel> {
        try {
            return articleDAO.queryForAll()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return emptyList()
    }

    fun isDuplicate(articleDataModelObj: ArticleDatabaseModel) : Boolean{
        try {
            val qb: QueryBuilder<ArticleDatabaseModel, Int> = articleDAO.queryBuilder()
            qb.where().eq(ArticleDatabaseModel.FIELD_NAME_TITLE, articleDataModelObj.title)
            qb.where().eq(ArticleDatabaseModel.FIELD_NAME_DATE, articleDataModelObj.date)
            qb.where().eq(ArticleDatabaseModel.FIELD_NAME_IMAGE_URL, articleDataModelObj.imageUrl)
            qb.where().eq(ArticleDatabaseModel.FIELD_NAME_WEB_URL, articleDataModelObj.webUrl)

            val pq: PreparedQuery<ArticleDatabaseModel> = qb.prepare()
            return  articleDAO.query(pq).size != 0
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return false
    }


}