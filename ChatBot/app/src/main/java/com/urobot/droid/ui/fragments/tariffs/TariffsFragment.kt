package com.urobot.droid.ui.fragments.tariffs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.urobot.droid.R
import com.urobot.droid.data.SharedManager
import kotlinx.android.synthetic.main.fragment_tariffs.*

class TariffsFragment : Fragment(), TariffsViewModel.ITariffsContract {

    companion object {
        fun newInstance() = TariffsFragment()
    }

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

            }
        })
        viewModel.setListener(this)
        val isPayment = SharedManager(context!!).paymentIsBuy
        if (isPayment) {
            payBill.setText("отключить")
        } else {
            payBill.setText("подключить")

        }
        val isCRM = SharedManager(context!!).crmIsBuy
        if (isCRM) {
            payCRM.setText("отключить")
        } else {
            payCRM.setText("подключить")

        }
        payBill.setOnClickListener {
            if (isPayment) {
                payBill.setText("подключить")
            } else {
                payBill.setText("отключить")
            }
            SharedManager(context!!).paymentIsBuy = !isPayment
        }
        payCRM.setOnClickListener {
            if (isCRM) {
                payCRM.setText("подключить")
            } else {
                payCRM.setText("отключить")
            }
            SharedManager(context!!).crmIsBuy = !isPayment
        }
    }

    override fun onTariffsResult(prmo: String) {
    }


}
