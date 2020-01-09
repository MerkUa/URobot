package com.urobot.droid.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.urobot.droid.R
import kotlinx.android.synthetic.main.dialog_fragment_create_event.*


class CreateEventDialogFragment : DialogFragment(), View.OnClickListener {

    companion object{
        private var instanceFragment: CreateEventDialogFragment? = null

        fun getInstance(): CreateEventDialogFragment? {
            instanceFragment =
                if (instanceFragment == null) CreateEventDialogFragment() else instanceFragment
            return instanceFragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         val view = inflater.inflate(R.layout.dialog_fragment_create_event, null)

//        save_button.setOnClickListener{
//            dismiss()
//        }

        view.findViewById<View>(R.id.add_buttons_tv).setOnClickListener(this)


        return view
    }


    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

    override fun onClick(v: View?) {
        if( v?.id == R.id.add_buttons_tv){
            add_buttons_tv.setOnClickListener {

                val bottomSheetFragment = context?.let { it -> BottomSheetDialog(it) }
                bottomSheetFragment?.setContentView(R.layout.bottom_sheet_add_buttons)
                bottomSheetFragment?.show()
            }
        }
    }
}