package com.urobot.droid.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.urobot.droid.R
import com.urobot.droid.data.model.ServiceButtons


class AddButtonBottomSheetDialog : BottomSheetDialogFragment() {

    private var mAddButtonBottomSheetListener: AddButtonBottomSheetListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.bottom_sheet_add_buttons, container, false)
        val writeButton = view.findViewById<Button>(R.id.add_write_button)
        val buttonPayment = view.findViewById<Button>(R.id.add_payment_button)
        val buttonNewLevel = view.findViewById<Button>(R.id.add_new_level)


        writeButton.setOnClickListener {
            mAddButtonBottomSheetListener?.ButtonClick(ServiceButtons(1, null))
        }

        buttonPayment.setOnClickListener {
            mAddButtonBottomSheetListener?.ButtonClick(ServiceButtons(2, null))
        }

        buttonNewLevel.setOnClickListener {

            mAddButtonBottomSheetListener?.ButtonClick(ServiceButtons(3, ""))
        }


        return view
    }


    fun setSelectedListener(listener: AddButtonBottomSheetListener) {
        this.mAddButtonBottomSheetListener = listener
    }

    interface AddButtonBottomSheetListener {
        fun ButtonClick(button: ServiceButtons)
    }

}