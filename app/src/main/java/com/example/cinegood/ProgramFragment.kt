package com.example.cinegood

import android.os.Bundle
import android.view.*
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_program.*


/**
 * A simple [Fragment] subclass.
 */
class ProgramFragment : Fragment() {

      override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
          //v e mWebView sono costanti, cambio R.layout.fragment_program  in modo che ritorna quello che mi serve
          val v = inflater.inflate(R.layout.fragment_program, container, false)
          val mWebView = v.findViewById<View>(R.id.webView) as WebView
          mWebView.loadUrl("https://big.stellafilm.stellafilm.it/ ")//mi collego a questo sito

          //abilito javascript
          val webSettings = mWebView.settings   
          webSettings.javaScriptEnabled = true


          //forzare l'apertura di collegamenti e reindirizzamenti nel webview
          mWebView.webViewClient = WebViewClient()
          mWebView.canGoBack()
          mWebView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
              if (keyCode == KeyEvent.KEYCODE_BACK
                  && event.action == MotionEvent.ACTION_UP
                  && mWebView.canGoBack()
              ) {
                  mWebView.goBack()
                  return@OnKeyListener true
              }
              false
          })


          return v
      }


          

}


