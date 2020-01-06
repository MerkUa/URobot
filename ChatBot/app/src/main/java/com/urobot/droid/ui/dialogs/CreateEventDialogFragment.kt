package com.urobot.droid.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.urobot.droid.R
import kotlinx.android.synthetic.main.dialog_fragment_create_event.*



class CreateEventDialogFragment : DialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        save_button.setOnClickListener{
            dismiss()
        }

        add_buttons_tv.setOnClickListener {

            val bottomSheetFragment = context?.let { it -> BottomSheetDialog(it) }
            bottomSheetFragment?.setContentView(R.layout.bottom_sheet_add_buttons)
            bottomSheetFragment?.show()
        }

        return inflater.inflate(R.layout.dialog_fragment_create_event, null)
    }
}