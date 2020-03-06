package com.urobot.droid.ui.fragments.addMesenger

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.urobot.droid.R
import com.urobot.droid.db.Messenger
import com.urobot.droid.db.User
import kotlinx.android.synthetic.main.add_telegram_fragment.*


class AddmessengerByTypeFragment : Fragment() {

    companion object {
        const val TELEGTAM = "Telegram"
        const val FACEBOOK = "Facebook"
        const val VIBER = "Viber"
        const val VK = "Vk"
        const val WHATSAPP = "Whatsapp"
    }

    private lateinit var viewModel: AddMessengerViewModel
    private lateinit var currentUser: User

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
        val type = arguments?.let { AddmessengerByTypeFragmentArgs.fromBundle(it).type }
        var messenger = Messenger.Telegram
        when (type) {
            TELEGTAM -> {
                MessengerImage.setImageDrawable(context?.getDrawable(R.drawable.telegram))
                messenger = Messenger.Telegram
            }
            VIBER -> {
                MessengerImage.setImageDrawable(context?.getDrawable(R.drawable.viber))
                messenger = Messenger.Viber
            }
            FACEBOOK -> {
                MessengerImage.setImageDrawable(context?.getDrawable(R.drawable.facebook))
                messenger = Messenger.Facebook
            }
            VK -> {
                MessengerImage.setImageDrawable(context?.getDrawable(R.drawable.vklogo))
                codeView.visibility = View.VISIBLE
                messenger = Messenger.Vk

            }

            WHATSAPP ->
                Log.d("Swich", "WHATSAPP")

        }
        sendTokenButton.setOnClickListener {
            viewModel.sendToken(
                tokenEditText.text.toString(),
                codeEditText.text.toString(),
                messenger
            )
        }
    }

}
