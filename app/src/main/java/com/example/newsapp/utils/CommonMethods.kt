package com.example.newsapp.utils


import android.content.Context
import android.content.SharedPreferences
import com.example.newsapp.BuildConfig
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger


class CommonMethods (context: Context) {

    private val SP_NAME: String  = "news_sp"
    private val prefs: SharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()
    private val KEY: String = "key"
    private val ALLOWED_CHARACTERS =
        "Mq1wNeBr2tVyCuXi3oZpAlSk4jDhFaGs5dHfJgKzLx6cPvObIn7mUY8TRE9WQ0"

    private lateinit var log: Logger

    init {
        initLogger()
        if(getKey().isNullOrEmpty()){
            generateRandomKey(10)?.let { setKey(it) }
        }

    }

    fun getKey() : String? {
        return prefs.getString(
            KEY,
            ""
        )
    }

    private fun setKey( key : String){
        editor.putString(KEY, key)
        editor.commit()
    }

    private fun generateRandomKey(length: Int): String? {
        val random = Random()
        val keyBuilder = StringBuilder(length)
        for (i in 0 until length) keyBuilder.append(
            ALLOWED_CHARACTERS.get(
                random.nextInt(
                    ALLOWED_CHARACTERS.length
                )
            )
        )
        return keyBuilder.toString()
    }


    fun printLog(TAG: String, message: String) {
        log.info("$TAG $message")
    }

    fun warningLog(TAG: String, message: String) {
        log.log(Level.WARNING, "$TAG $message")
    }

    fun warningLog(
        TAG: String,
        message: String,
        t: Throwable?
    ) {
        log.log(Level.WARNING, "$TAG $message", t)
    }

    private fun initLogger() {
        log = Logger.getLogger("DEBUG")
        if (BuildConfig.DEBUG) {
            log.setLevel(Level.ALL)
        } else {
            log.setLevel(Level.OFF)
        }
    }




}