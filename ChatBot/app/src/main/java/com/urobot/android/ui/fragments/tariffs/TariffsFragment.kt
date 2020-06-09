package com.urobot.android.ui.fragments.tariffs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.urobot.android.R
import com.urobot.android.data.model.cmsModel
import kotlinx.android.synthetic.main.fragment_tariffs.*

class TariffsFragment : Fragment(), TariffsViewModel.ITariffsContract {

    private var userId = "0"
    private var userToken = "0"


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
                userToken = it.token!!
                viewModel.getAllIndustryFromNet(userToken)
            }
        })
        var url = ""
        viewModel.setListener(this)

        tariffCard.setOnClickListener {
            showWeb("subscription", userId)
        }

        payBill.setOnClickListener {
            showWeb("billing_account", userId)
        }
        pay1C.setOnClickListener {
            showWeb("1c", userId)
        }
        payAMO.setOnClickListener {
            showWeb("amo", userId)
        }
        payInsta.setOnClickListener {
            showWeb("instagram", userId)
        }
        payWA.setOnClickListener {
            showWeb("whatsapp", userId)
        }
        payBucket.setOnClickListener {
            showWeb("cart", userId)
        }


    }

    override fun onTariffsResult(crmList: List<cmsModel>) {
        for (item in crmList) {
            when (item.key) {
                "billing_account" -> {
                    if (item.active) {
                        payBill.setText(R.string.off)
                    } else {
                        payBill.setText(R.string.on)
                    }
                    tvChangeBill.text = item.price
                }
                "1c" -> {
                    if (item.active) {
                        pay1C.setText(R.string.off)
                    } else {
                        pay1C.setText(R.string.on)
                    }
                    tvChange1C.text = item.price

                }
                "amo" -> {
                    if (item.active) {
                        payAMO.setText(R.string.off)
                    } else {
                        payAMO.setText(R.string.on)
                    }
                    tvChangeAMO.text = item.price
                }
                "instagram" -> {
                    if (item.active) {
                        payInsta.setText(R.string.off)
                    } else {
                        payInsta.setText(R.string.on)
                    }
                    tvChangeInsta.text = item.price

                }
                "whatsapp" -> {
                    if (item.active) {
                        payWA.setText(R.string.off)
                    } else {
                        payWA.setText(R.string.on)
                    }
                    tvChangeWA.text = item.price

                }
                "cart" -> {
                    if (item.active) {
                        payBucket.setText(R.string.off)
                    } else {
                        payBucket.setText(R.string.on)
                    }
                    tvChangeBucket.text = item.price

                }
                "subscription" -> {
                    title.text = item.name
                    tvChange.text = item.price
                }
            }
        }
    }

    fun showWeb(key: String, id: String) {
        paymentsScroll.visibility = View.GONE

        val url = "https://urobot.ml/system/user/update-service/" + key + "/" + id
//        payments.visibility = View.GONE

        webView.visibility = View.VISIBLE
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url == "https://urobot.ml/system/user/update-service/success") {
                    webView.visibility = View.GONE
                    paymentsScroll.visibility = View.VISIBLE
                    viewModel.getAllIndustryFromNet(userToken)
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
