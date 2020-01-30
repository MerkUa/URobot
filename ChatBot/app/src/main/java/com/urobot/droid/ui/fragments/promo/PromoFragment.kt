package com.urobot.droid.ui.fragments.promo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.urobot.droid.R
import kotlinx.android.synthetic.main.promo_fragment.*

class PromoFragment : Fragment(), PromoViewModel.IPromoContract {

    companion object {
        fun newInstance() = PromoFragment()
    }

    private lateinit var viewModel: PromoViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.promo_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PromoViewModel::class.java)
        viewModel.User.observe(viewLifecycleOwner, Observer { users ->
            // Update the cached copy of the words in the adapter.
            users?.let {
                viewModel.getPromo(it.token!!)
            }
        })
        viewModel.setListener(this)
    }

    override fun onPromoResult(promo: String) {
        sharePromo.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Это Ваш Промокод " + promo);
            startActivity(Intent.createChooser(shareIntent, "Отправить промокод другу"))
        }
    }

}
