package com.urobot.droid.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.urobot.droid.R
import com.urobot.droid.data.model.PaymentModel
import com.urobot.droid.data.model.PaymentTypes
import kotlinx.android.synthetic.main.fragment_bottom_payment.*


class BottomPaymentFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bottom_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createPaymentBotButton.setOnClickListener{


            val namePaymentService = namePaymentEditText.text.toString()

            val cardName =  ownerPaymentEditText.text.toString()
            val cardNumber = numberPaymentEditText.text.toString()
            val month = phoneEditText.unmaskedText.subSequence(0, 2).toString()
            val year = phoneEditText.unmaskedText.subSequence(2, phoneEditText.unmaskedText.length).toString()
            val cvv = cvvPaymentEditText.text.toString()

            val dataPayment = PaymentModel(cardNumber,cardName, month, year, cvv, listOf(PaymentTypes.CreditCard.type) )

            val action = BottomPaymentFragmentDirections
                .actionNavigationCreatePaymentToNavigationServicesFragment()
                .setPaymentModel(dataPayment)
                .setNamePaymentService(namePaymentService)

            Navigation.findNavController(view).navigate(action)
        }
    }
}
