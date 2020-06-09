package com.urobot.android.ui.fragments.addMesenger

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.urobot.android.R
import com.urobot.android.data.SharedManager
import com.urobot.android.db.Messenger
import com.urobot.android.db.User
import kotlinx.android.synthetic.main.add_telegram_fragment.*


class AddmessengerByTypeFragment : Fragment() {

    companion object {
        const val TELEGTAM = "Telegram"
        const val FACEBOOK = "Facebook"
        const val VIBER = "Viber"
        const val VK = "Vk"
        const val WHATSAP = "Whatsapp"
        const val INSTAGRAM = "Instagram"

    }

    private lateinit var viewModel: AddMessengerViewModel
    private lateinit var currentUser: User
    private var messenger = Messenger.Telegram
    private var userId = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(AddMessengerViewModel::class.java)

        viewModel.currentUser.observe(viewLifecycleOwner, Observer { users ->
            // Update the cached copy of the words in the adapter.
            users?.let {
                currentUser = it
                userId = it.id
                Log.d("Merk", "Merk " + userId)
                init()
            }
        })
        return inflater.inflate(R.layout.add_telegram_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddMessengerViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
//        val type = arguments?.let { AddmessengerByTypeFragmentArgs.fromBundle(it).type }

    }

    @SuppressLint("SetJavaScriptEnabled")
    fun init() {
        val type = arguments?.let { AddmessengerByTypeFragmentArgs.fromBundle(it).type }
        var messenger = Messenger.Telegram
        when (type) {
            TELEGTAM -> {
                messenger = Messenger.Telegram
            }
            VIBER -> {
                messenger = Messenger.Viber
            }
            FACEBOOK -> {
                messenger = Messenger.Facebook
            }
            VK -> {
                messenger = Messenger.Vk

            }

            WHATSAP -> {
                messenger = Messenger.WhatsApp
            }
            INSTAGRAM -> {
                messenger = Messenger.Instagram
            }

        }
        webView.visibility = View.VISIBLE
        tokenLayout.visibility = View.GONE
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url == "https://urobot.ml/system/services/messenger-login/success") {
                    activity?.onBackPressed()
                    SharedManager(context!!).telegramIsConnected = true
                }
                view?.loadUrl(url)
                return true
            }
        }
        val url =
            "https://urobot.ml/system/services/messenger-login/" + userId + "/" + messenger.messengerId
        Log.d("Merk", "Merk " + url)
        webView.settings.javaScriptEnabled = true;
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.loadUrl(url)
        sendTokenButton.setOnClickListener {
            viewModel.sendToken(
                tokenEditText.text.toString(),
                codeEditText.text.toString(),
                messenger
            )
        }
    }

}
