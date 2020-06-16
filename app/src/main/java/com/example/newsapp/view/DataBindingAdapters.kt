package com.example.newsapp.view

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 */
object DataBindingAdapters{

    /**
     * Method to set ImageView by image URL
     *
     * @param imageView ImageView
     * @param imageUrl String
     */
    @JvmStatic
    @BindingAdapter("android:imageUrl")
    fun imageUrl(imageView: ImageView, imageUrl: String) {
        Picasso.get()
            .load(imageUrl)
            .into(imageView)
    }

    /**
     * Method to set date & time with custom format into textview
     *
     * @param textView TextView
     * @param timestamp Long
     */
    @JvmStatic
    @BindingAdapter("android:setDate")
    fun setDate(textView: TextView, timestamp: String){

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val date = sdf.parse(timestamp)

        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
        outputFormat.timeZone = TimeZone.getTimeZone("Asia/Dhaka")

        textView.text = outputFormat.format(date)
    }



    /**
     * Method to set date & time with custom format into textview
     *
     * @param textView TextView
     * @param timestamp Long
     */
    @JvmStatic
    @BindingAdapter("android:setTime")
    fun setTime(textView: TextView, timestamp: String){

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val date = sdf.parse(timestamp)

        val outputFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        outputFormat.timeZone = TimeZone.getTimeZone("Asia/Dhaka")

        textView.text = outputFormat.format(date)
    }
}
