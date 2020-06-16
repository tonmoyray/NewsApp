package com.example.newsapp.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.component
import com.example.newsapp.utils.CommonMethods
import com.example.newsapp.utils.Constants
import org.jetbrains.anko.sdk25.coroutines.onKey
import javax.inject.Inject


class NewsDetailsFragment : Fragment() {

    lateinit var webView: WebView
    var htmlUrl = String()
    lateinit var progressBar : ProgressBar
    lateinit var parentActivity: MainActivity
    private var TAG : String  = NewsDetailsFragment::class.java.simpleName

    @Inject
    lateinit var commonMethods: CommonMethods

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        commonMethods.printLog(TAG, "onCreate")
        htmlUrl = arguments?.getString(Constants.HTML_DATA_TAG).toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_news_details, container, false)

        webView = view.findViewById(R.id.webView)
        progressBar = view.findViewById(R.id.progressBar)

        view.onKey { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){  //TODO does not work
                webView.goBack()
            }
        }
        initWebView()
        webView.loadUrl(htmlUrl)

        parentActivity = activity as MainActivity
        return view
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.domStorageEnabled = true
        webView.isSoundEffectsEnabled = true
        webView.webChromeClient = ChromeClient()
    }

    inner class ChromeClient internal constructor() : WebChromeClient() {
        private var mCustomView: View? = null
        private var mCustomViewCallback: CustomViewCallback? = null
        protected var mFullscreenContainer: FrameLayout? = null
        private var mOriginalOrientation = 0
        private var mOriginalSystemUiVisibility = 0
        override fun getDefaultVideoPoster(): Bitmap? {
            return if (mCustomView == null) {
                null
            } else BitmapFactory.decodeResource(
                parentActivity.getApplicationContext().getResources(),
                2130837573
            )
        }

        override fun onHideCustomView() {
            (parentActivity.getWindow().getDecorView() as FrameLayout).removeView(mCustomView)
            mCustomView = null
            parentActivity.getWindow().getDecorView().setSystemUiVisibility(mOriginalSystemUiVisibility)
            parentActivity.setRequestedOrientation(mOriginalOrientation)
            mCustomViewCallback!!.onCustomViewHidden()
            mCustomViewCallback = null
        }

        override fun onShowCustomView(
            paramView: View,
            paramCustomViewCallback: CustomViewCallback
        ) {
            if (mCustomView != null) {
                onHideCustomView()
                return
            }
            mCustomView = paramView
            mOriginalSystemUiVisibility = parentActivity.getWindow().getDecorView().getSystemUiVisibility()
            mOriginalOrientation = parentActivity.getRequestedOrientation()
            mCustomViewCallback = paramCustomViewCallback
            (parentActivity.getWindow().getDecorView() as FrameLayout).addView(
                mCustomView,
                FrameLayout.LayoutParams(-1, -1)
            )
            parentActivity.getWindow().getDecorView()
                .setSystemUiVisibility(3846 or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            progressBar.progress = newProgress
            if (newProgress < 100 && progressBar.visibility == ProgressBar.GONE) {
                progressBar.visibility = ProgressBar.VISIBLE
            }
            if (newProgress == 100) {
                progressBar.visibility = ProgressBar.GONE
            }
        }
    }
}
