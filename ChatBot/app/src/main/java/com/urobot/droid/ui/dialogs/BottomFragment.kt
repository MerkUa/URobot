package com.urobot.droid.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.urobot.droid.R
import com.urobot.droid.data.SharedManager

class BottomFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: ButtomViewModel
    private var mBottomSheetListener: BottomSheetListener? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.bottom_sheet_service, container, false)
        val buttonCalendar = root.findViewById<Button>(R.id.calendarButton)
        buttonCalendar.setOnClickListener {
            mBottomSheetListener?.onCalendarClick()
        }
        val buttonPayment = root.findViewById<Button>(R.id.paymentButton)
        buttonPayment.setOnClickListener {
            mBottomSheetListener?.onPaymentClick()
        }
        val buttonPaymentCustom = root.findViewById<Button>(R.id.paymentCustomButton)
        val isPayment = SharedManager(context!!).paymentIsBuy
        if (isPayment) {
            buttonPaymentCustom.visibility = View.VISIBLE
        }
        buttonPayment.setOnClickListener {
            mBottomSheetListener?.onPaymentCustomClick()
        }


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ButtomViewModel::class.java)
    }

    fun setSelectedListener(listener: BottomSheetListener) {
        this.mBottomSheetListener = listener
    }

    interface BottomSheetListener {
        fun onCalendarClick()
        fun onPaymentClick()
        fun onPaymentCustomClick()

    }

}

