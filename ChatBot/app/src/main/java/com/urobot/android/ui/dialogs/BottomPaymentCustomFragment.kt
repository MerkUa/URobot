package com.urobot.android.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.urobot.android.R
import com.urobot.android.data.model.PaymentModel
import com.urobot.android.data.model.PaymentTypes
import kotlinx.android.synthetic.main.fragment_bottom_payment.createPaymentBotButton
import kotlinx.android.synthetic.main.fragment_bottom_payment.deleteBtn
import kotlinx.android.synthetic.main.fragment_bottom_payment.descriptionPaymentEditText
import kotlinx.android.synthetic.main.fragment_bottom_payment.namePaymentEditText
import kotlinx.android.synthetic.main.fragment_bottom_payment.numberPaymentEditText
import kotlinx.android.synthetic.main.fragment_bottom_payment.ownerPaymentEditText
import kotlinx.android.synthetic.main.fragment_bottom_payment.periodEditText
import kotlinx.android.synthetic.main.fragment_bottom_payment.pricePaymentEditText
import kotlinx.android.synthetic.main.fragment_bottom_payment.updatePaymentBotButton

class BottomPaymentCustomFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (BottomPaymentFragmentArgs.fromBundle(arguments!!).paymentData != null) {

            createPaymentBotButton.visibility = View.GONE
            updatePaymentBotButton.visibility = View.VISIBLE
            deleteBtn.visibility = View.VISIBLE

            val paymentData = BottomPaymentFragmentArgs.fromBundle(arguments!!).paymentData

            namePaymentEditText.setText(paymentData?.name)
            pricePaymentEditText.setText(paymentData?.data?.price)
            descriptionPaymentEditText.setText(paymentData?.description)
            ownerPaymentEditText.setText(paymentData?.data?.card_name)
            numberPaymentEditText.setText(paymentData?.data?.cardNumber)

            val monthAndYearTextFromNet = paymentData?.data?.month + paymentData?.data?.year

            periodEditText.setMaskedText(monthAndYearTextFromNet)


            updatePaymentBotButton.setOnClickListener {

                if (periodEditText.unmaskedText.isNotEmpty() || periodEditText.unmaskedText.length == 6) {

                    val month = periodEditText?.unmaskedText?.subSequence(0, 2).toString()
                    val year = periodEditText?.unmaskedText?.subSequence(
                        2,
                        periodEditText.unmaskedText.length
                    ).toString()

                    val data = PaymentModel(
                        ownerPaymentEditText.text.toString(),
                        numberPaymentEditText.textAlignment.toString(),
                        month, year, "",
                        pricePaymentEditText.text.toString(),
                        listOf(PaymentTypes.CreditCard.type)
                    )
                    val id = BottomPaymentFragmentArgs.fromBundle(arguments!!).serviceId

                    val action =
                        BottomPaymentFragmentDirections.actionNavigationCreatePaymentToNavigationServicesFragment()
                            .setServiceId(id)
                            .setUpdatePaymentSevice(data)
                    Navigation.findNavController(view).navigate(action)
                } else {
                    Toast.makeText(
                        context,
                        "Please Check Field: validity period of the card, ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


        createPaymentBotButton.setOnClickListener {

            val namePaymentService = namePaymentEditText.text.toString()

            val cardName = ownerPaymentEditText.text.toString()
            val cardNumber = numberPaymentEditText.text.toString()
//            val cvv = cvvPaymentEditText.text.toString()

            if (periodEditText.unmaskedText.isNotEmpty() || periodEditText.unmaskedText.length == 6) {

                val month = periodEditText?.unmaskedText?.subSequence(0, 2).toString()
                val year =
                    periodEditText?.unmaskedText?.subSequence(2, periodEditText.unmaskedText.length)
                        .toString()

                val dataPayment = PaymentModel(
                    cardNumber,
                    cardName,
                    month,
                    year,
                    "",
                    pricePaymentEditText.text.toString(),
                    listOf(PaymentTypes.CreditCard.type)
                )

                val action = BottomPaymentFragmentDirections
                    .actionNavigationCreatePaymentToNavigationServicesFragment()
                    .setPaymentModel(dataPayment)
                    .setNamePaymentService(namePaymentService)

                Navigation.findNavController(view).navigate(action)
            } else {
                Toast.makeText(
                    context,
                    "Please Check Field: validity period of the card, ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
