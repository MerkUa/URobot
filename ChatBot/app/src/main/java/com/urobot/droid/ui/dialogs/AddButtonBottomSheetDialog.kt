package com.urobot.droid.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.urobot.droid.R


class AddButtonBottomSheetDialog : BottomSheetDialogFragment() {

    private var mAddButtonBottomSheetListener: AddButtonBottomSheetListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.bottom_sheet_add_buttons, container, false)
        val writeButton = view.findViewById<Button>(R.id.add_write_button)
        val buttonPayment = view.findViewById<Button>(R.id.add_payment_button)

        writeButton.setOnClickListener {
            mAddButtonBottomSheetListener?.onWriteButtonClick()
        }

        buttonPayment.setOnClickListener {
            mAddButtonBottomSheetListener?.onPaymentClick()
        }


        return view
    }


    fun setSelectedListener(listener: AddButtonBottomSheetListener) {
        this.mAddButtonBottomSheetListener = listener
    }

    interface AddButtonBottomSheetListener {
        fun onWriteButtonClick()
        fun onPaymentClick()
    }

}