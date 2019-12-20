package com.urobot.droid.ui.fragments.promo

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider

import com.urobot.droid.R
import com.urobot.droid.ui.fragments.addMesenger.AddMessengerFragment
import com.urobot.droid.ui.main.MainChatActivity
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.promo_fragment.*

class PromoFragment : Fragment() {

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
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharePromo.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Это Ваш Промокод");
            startActivity(Intent.createChooser(shareIntent, "Отправить промокод другу"))
        }
    }

}
