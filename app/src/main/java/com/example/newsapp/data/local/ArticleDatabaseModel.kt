package com.example.newsapp.data.local

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = ArticleDatabaseModel.TABLE_NAME_ARTICLE)
class ArticleDatabaseModel {

    companion object{
        const val TABLE_NAME_ARTICLE = "article"
        const val FIELD_NAME_ID = "id"
        const val FIELD_NAME_TITLE = "title"
        const val FIELD_NAME_DATE = "date"
        const val FIELD_NAME_IMAGE_URL = "image_url"
        const val FIELD_NAME_WEB_URL = "web_url"
    }


    @DatabaseField(
        generatedId = true,
        columnName = FIELD_NAME_ID
    )
    var id = 0


    @DatabaseField(
        canBeNull = false,
        dataType = DataType.STRING,
        columnName = FIELD_NAME_TITLE
    )
    var title: String = ""



    @DatabaseField(
        canBeNull = false,
        dataType = DataType.STRING,
        columnName = FIELD_NAME_DATE
    )
    var date: String = ""



    @DatabaseField(
        canBeNull = false,
        dataType = DataType.STRING,
        columnName = FIELD_NAME_IMAGE_URL
    )
    var imageUrl: String = ""




    @DatabaseField(
        canBeNull = false,
        dataType = DataType.STRING,
        columnName = FIELD_NAME_WEB_URL
    )
    var webUrl: String = ""


}