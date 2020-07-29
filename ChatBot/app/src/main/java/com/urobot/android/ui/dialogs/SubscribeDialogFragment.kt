package com.urobot.android.ui.dialogs

import android.app.Dialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import com.urobot.android.R
import kotlinx.android.synthetic.main.dialog_fragment_choose_messenger.*


class SubscribeDialogFragment : DialogFragment() {

    private var userId = ""
    private var robotId = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.dialog_fragment_webview, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = "https://urobot.ml/system/user/update-service/subscription/" + userId + "/"
        webView.settings.javaScriptEnabled = true;
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url == "https://urobot.ml/system/user/update-service/subscription/success") {
                    val PREF_NAME = "SHOW_SUBSCRIBE"
                    val sharedPref: SharedPreferences? =
                        activity?.getSharedPreferences(PREF_NAME, 0)
                    sharedPref?.edit()?.putBoolean(PREF_NAME, false)
                    dismiss()
                }
                view?.loadUrl(url)
                return true
            }
        }
        webView.loadUrl(url)
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog.window?.setLayout(width, height)
        }
    }

    fun setUserId(user: String) {
        userId = user
    }
}
