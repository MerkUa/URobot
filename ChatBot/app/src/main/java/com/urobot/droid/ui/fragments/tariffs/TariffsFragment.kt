package com.urobot.droid.ui.fragments.tariffs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.urobot.droid.R
import com.urobot.droid.data.model.cmsModel
import com.urobot.droid.data.model.cmsType
import kotlinx.android.synthetic.main.fragment_tariffs.*

class TariffsFragment : Fragment(), TariffsViewModel.ITariffsContract {

    private var userId = "0"

    private lateinit var viewModel: TariffsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tariffs, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TariffsViewModel::class.java)
        viewModel.User.observe(viewLifecycleOwner, Observer { users ->
            // Update the cached copy of the words in the adapter.
            users?.let {
                userId = it.id
                viewModel.getAllIndustryFromNet(it.token!!)
            }
        })
        var url = ""
        viewModel.setListener(this)

        payBill.setOnClickListener {
            url = "https://urobot-dev.ml/system/user/update-billing/" + userId
            payments.visibility = View.GONE
            webView.visibility = View.VISIBLE
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    if (url == "https://urobot-dev.ml/system/user/update-cms/success") {
                        webView.visibility = View.GONE
                        payBill.setText("отключить")

                    }
                    view?.loadUrl(url)
                    return true
                }
            }
            webView.settings.javaScriptEnabled = true;
            webView.settings.javaScriptCanOpenWindowsAutomatically = true
            webView.loadUrl(url)

        }
        payCRM.setOnClickListener {
            payments.visibility = View.GONE
            url = "https://urobot-dev.ml/system/user/update-cms/" + userId
            webView.visibility = View.VISIBLE
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    if (url == "https://urobot-dev.ml/system/user/update-cms/success") {
                        webView.visibility = View.GONE
                        payCRM.setText("отключить")
                    }
                    view?.loadUrl(url)
                    return true
                }
            }
            webView.settings.javaScriptEnabled = true;
            webView.settings.javaScriptCanOpenWindowsAutomatically = true
            webView.loadUrl(url)
        }


    }

    override fun onTariffsResult(crmList: List<cmsModel>) {
        for (item in crmList) {
            when (cmsType.Companion.fromValue(item.id)) {
                cmsType.BillingAccount -> {
                    if (item.active) {
                        payBill.setText(R.string.off)
                    } else {
                        payBill.setText(R.string.on)
                    }
                }
                cmsType.OneC -> {
                    if (item.active) {
                        payCRM.setText(R.string.off)
                    } else {
                        payCRM.setText(R.string.on)
                    }
                }
                cmsType.Amo -> {
                    if (item.active) {
                        payCRM.setText(R.string.off)
                    } else {
                        payCRM.setText(R.string.on)
                    }
                }
            }
        }
    }


}
