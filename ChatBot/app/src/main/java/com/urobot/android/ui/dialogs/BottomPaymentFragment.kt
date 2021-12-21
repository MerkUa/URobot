package com.urobot.android.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.urobot.android.R
import com.urobot.android.data.model.PaymentModel
import com.urobot.android.data.model.PaymentTypes
import com.urobot.android.data.model.TypeServices
import com.urobot.android.db.User
import kotlinx.android.synthetic.main.fragment_bottom_payment.createPaymentBotButton
import kotlinx.android.synthetic.main.fragment_bottom_payment.deleteBtn
import kotlinx.android.synthetic.main.fragment_bottom_payment.descriptionPaymentEditText
import kotlinx.android.synthetic.main.fragment_bottom_payment.descriptionPaymentLayout
import kotlinx.android.synthetic.main.fragment_bottom_payment.namePaymentEditText
import kotlinx.android.synthetic.main.fragment_bottom_payment.namePaymentLayout
import kotlinx.android.synthetic.main.fragment_bottom_payment.numberPaymentEditText
import kotlinx.android.synthetic.main.fragment_bottom_payment.numberPaymentLayout
import kotlinx.android.synthetic.main.fragment_bottom_payment.ownerPaymentEditText
import kotlinx.android.synthetic.main.fragment_bottom_payment.periodEditText
import kotlinx.android.synthetic.main.fragment_bottom_payment.pricePaymentEditText
import kotlinx.android.synthetic.main.fragment_bottom_payment.updatePaymentBotButton

class BottomPaymentFragment : Fragment(), PaymentsViewModel.IPaymentsContract {

    private lateinit var viewModel: PaymentsViewModel
    private lateinit var user: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(PaymentsViewModel::class.java)
        viewModel.currentUser.observe(viewLifecycleOwner, Observer { users ->
            users?.let {
                user = it
            }
        })
        return inflater.inflate(R.layout.fragment_bottom_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setListener(this)
        if( BottomPaymentFragmentArgs.fromBundle(arguments!!).paymentData != null ){

            createPaymentBotButton.visibility = View.GONE
            updatePaymentBotButton.visibility = View.VISIBLE
            deleteBtn.visibility = View.VISIBLE

            val paymentData = BottomPaymentFragmentArgs.fromBundle(arguments!!).paymentData

            namePaymentEditText.setText(paymentData?.name)
            descriptionPaymentEditText.setText(paymentData?.description)
            pricePaymentEditText.setText(paymentData?.data?.price)
            ownerPaymentEditText.setText(paymentData?.data?.card_name)
            numberPaymentEditText.setText(paymentData?.data?.cardNumber)

            val monthAndYearTextFromNet = paymentData?.data?.month + paymentData?.data?.year

            periodEditText.setMaskedText(monthAndYearTextFromNet)

            deleteBtn.setOnClickListener {
                user.token?.let { it1 ->
                    val id = BottomPaymentFragmentArgs.fromBundle(arguments!!).serviceId
                    viewModel.deletePaymentServices(it1, id)
                }
            }


            updatePaymentBotButton.setOnClickListener{

                if (periodEditText.unmaskedText.isNotEmpty() || periodEditText.unmaskedText.length == 6) {

                    val namePaymentService = namePaymentEditText.text.toString()
                    val description = descriptionPaymentEditText.text.toString()
                    val month = periodEditText?.unmaskedText?.subSequence(0, 2).toString()
                    val year = periodEditText?.unmaskedText?.subSequence(2, periodEditText.unmaskedText.length).toString()

                    val data = PaymentModel(
                        ownerPaymentEditText.text.toString(),
                        numberPaymentEditText.textAlignment.toString(),
                        month, year, "",
                        pricePaymentEditText.text.toString(),
                        listOf(PaymentTypes.CreditCard.type)
                    )
                    val id = BottomPaymentFragmentArgs.fromBundle(arguments!!).serviceId

                    user.token?.let { it1 ->
                        viewModel.updatePaymentServices(
                            namePaymentService, description,
                            it1, data, id, TypeServices.payment.type_id
                        )
                    }

//                   val action = BottomPaymentFragmentDirections.actionNavigationCreatePaymentToNavigationServicesFragment()
//                        .setServiceId(id)
//                        .setUpdatePaymentSevice(data)
//                        .setNamePaymentService(namePaymentService)
//                        .setDescriptionPaymentService(description)
//                    Navigation.findNavController(view).navigate(action)

                } else {
                    Toast.makeText(context, "Please Check Field: validity period of the card, ", Toast.LENGTH_SHORT).show()
                }
            }
        }


        createPaymentBotButton.setOnClickListener {
            if (validate()) {
                val namePaymentService = namePaymentEditText.text.toString()
                val description = descriptionPaymentEditText.text.toString()
                val cardName = ownerPaymentEditText.text.toString()
                val cardNumber = numberPaymentEditText.text.toString()

                if (periodEditText.unmaskedText.isNotEmpty() || periodEditText.unmaskedText.length == 6) {

                    val month = periodEditText?.unmaskedText?.subSequence(0, 2).toString()
                    val year = periodEditText?.unmaskedText?.subSequence(2, periodEditText.unmaskedText.length).toString()

                    val dataPayment = PaymentModel(
                        cardNumber,
                        cardName,
                        month,
                        year,
                        "",
                        pricePaymentEditText.text.toString(),
                        listOf(PaymentTypes.CreditCard.type)
                    )

                    user.token?.let { it1 ->
                        viewModel.createPaymentService(
                            namePaymentService, description,
                            it1, dataPayment, TypeServices.payment.type_id
                        )
                    }
                } else {
                    Toast.makeText(context, "Please Check Field: validity period of the card, ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun validate(): Boolean {
        val namePaymentService = namePaymentEditText.text.toString()
        val description = descriptionPaymentEditText.text.toString()
        val cardNumber = numberPaymentEditText.text.toString()
        if (namePaymentService.isEmpty()) {
            namePaymentLayout.error = getString(R.string.required)
        }
        if (description.isEmpty()) {
            descriptionPaymentLayout.error = getString(R.string.required)
        }
        if (cardNumber.isEmpty()) {
            numberPaymentLayout.error = getString(R.string.required)
        }
        if (periodEditText.unmaskedText.isEmpty()) {
            periodEditText.error = getString(R.string.required)
        }
        return namePaymentService.isNotEmpty() || description.isNotEmpty() || cardNumber.isNotEmpty() || periodEditText.unmaskedText.isNotEmpty()
    }

    override fun onPaymentsResult() {
        activity!!.onBackPressed()
    }
}
