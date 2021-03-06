package com.urobot.android.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import com.urobot.android.R
import com.urobot.android.db.Messenger
import kotlinx.android.synthetic.main.dialog_fragment_choose_messenger.*


class ChooseMessengerDialogFragment : DialogFragment() {

    private var onClickListener: OnMessengerClickListener? = null
    var messengerId: Int = 0
    var userId: Int = 0
    private var robotId = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.dialog_fragment_choose_messenger, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = "https://urobot.ml/system/services/bot-registration/" + robotId + "/"
        webView.settings.javaScriptEnabled = true;
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url == "https://urobot.ml/system/services/bot-registr/success") {
//                    activity?.onBackPressed()
                    dismiss()
                    onClickListener?.messengerAdded()
                }
                view?.loadUrl(url)
                return true
            }
        }

        faceBookLayout.setOnClickListener {
            linearLayoutMessengers.visibility = View.GONE
            linearLayoutWebView.visibility = View.VISIBLE
            messengerId = Messenger.Facebook.messengerId
//            onClickListener?.messengerAdded()
            webView.loadUrl(url + messengerId)

        }
        telegramLayout.setOnClickListener {
            linearLayoutMessengers.visibility = View.GONE
//            linearLayoutText.visibility = View.VISIBLE

            linearLayoutWebView.visibility = View.VISIBLE

            messengerId = Messenger.Telegram.messengerId
            webView.loadUrl(url + messengerId)

        }
        vkLayout.setOnClickListener {
            linearLayoutMessengers.visibility = View.GONE
//            linearLayoutText.visibility = View.VISIBLE
//            codeLayout.visibility = View.VISIBLE
            linearLayoutWebView.visibility = View.VISIBLE

            messengerId = Messenger.Vk.messengerId
            webView.loadUrl(url + messengerId)

        }
        viberLayout.setOnClickListener {
            linearLayoutMessengers.visibility = View.GONE
            linearLayoutWebView.visibility = View.VISIBLE

            messengerId = Messenger.Viber.messengerId
            webView.loadUrl(url + messengerId)

        }
        istaLayout.setOnClickListener {
            linearLayoutMessengers.visibility = View.GONE
            linearLayoutWebView.visibility = View.VISIBLE
            messengerId = Messenger.Instagram.messengerId
            webView.loadUrl(url + messengerId)

//            webView.loadUrl(url)
        }
        waLayout.setOnClickListener {
            linearLayoutMessengers.visibility = View.GONE
            linearLayoutWebView.visibility = View.VISIBLE

            messengerId = Messenger.WhatsApp.messengerId
            Log.d("Merk", "URL " + url + messengerId)
            webView.loadUrl(url + messengerId)

        }
        addBotButton.setOnClickListener {
            onClickListener?.onClickListener(
                robotId,
                messengerId,
                tokenEditText.text.toString(),
                codeEditText.text.toString()
            )
            dismiss()
        }
    }

    fun setSelectedListener(listener: OnMessengerClickListener) {
        this.onClickListener = listener
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

    fun setRobotId(botId: String) {
        robotId = botId
    }

    fun setUserId(botId: String) {
        robotId = botId
    }

    interface OnMessengerClickListener {
        fun onClickListener(robotId: String, messengerId: Int, token: String, code: String)
        fun messengerAdded()
    }
}
