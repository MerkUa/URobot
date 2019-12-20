package com.urobot.droid.ui.fragments.addMesenger

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.urobot.droid.R
import kotlinx.android.synthetic.main.add_telegram_fragment.*


class AddmessengerByTypeFragment : Fragment() {

    companion object {
        const val TELEGTAM = "Telegram"
        const val VIBER = "Viber"
        const val WHATSAPP = "Whatsapp"
    }

    private lateinit var viewModel: AddTelegramViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_telegram_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddTelegramViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val type = AddmessengerByTypeFragmentArgs.fromBundle(arguments).type
        when (type) {
            TELEGTAM -> {
                Log.d("Swich", "TELEGTAM")
                MessengerImage.setImageDrawable(context?.getDrawable(R.drawable.telegram))
            }
            VIBER ->
                Log.d("Swich", "VIBER")

            WHATSAPP ->
                Log.d("Swich", "WHATSAPP")

        }
    }

}
