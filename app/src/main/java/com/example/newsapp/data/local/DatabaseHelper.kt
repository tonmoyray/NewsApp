package com.example.newsapp.data.local

import android.content.Context
import com.example.newsapp.utils.CommonMethods
import com.j256.ormlite.cipher.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import net.sqlcipher.database.SQLiteDatabase
import java.sql.SQLException


class DatabaseHelper : OrmLiteSqliteOpenHelper {

    private var articleDAO: Dao<ArticleDatabaseModel, Int>? = null
    private var context: Context

    constructor(
        context: Context
    ) : super(context, "news_app.db", null, 1) { //TODO  DB name & version should not be hardcoded here
        this.context = context
        SQLiteDatabase.loadLibs(context)
    }


    override fun onCreate(
        database: SQLiteDatabase?,
        connectionSource: ConnectionSource?
    ) {
        TableUtils.createTable(
            connectionSource,
            ArticleDatabaseModel::class.java
        )
    }

    override fun onUpgrade(
        database: SQLiteDatabase?,
        connectionSource: ConnectionSource?,
        oldVersion: Int,
        newVersion: Int
    ) {
        //TODO
    }

    @Throws(SQLException::class)
    fun getArticleDao(): Dao<ArticleDatabaseModel, Int>? {
        if (articleDAO == null) {
            articleDAO =
                getDao(
                    ArticleDatabaseModel::class.java
                )
        }
        return articleDAO
    }

    override fun getPassword(): String {
        var commonMethods = CommonMethods(context) // TODO should be injected
        return commonMethods.getKey().toString()
    }
}