package com.urobot.droid.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.urobot.droid.R
import com.urobot.droid.data.NetModel.Request.RequestCreateBot
import kotlinx.android.synthetic.main.dialog_fragment_choose_messenger.*


class ChooseMessengerDialogFragment : DialogFragment() {

    private var onClickListener: OnMessengerClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.dialog_fragment_choose_messenger, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        faceBookLayout.setOnClickListener {
            onClickListener?.onClickListener(RequestCreateBot.FACEBOOK)
            dismiss()
        }
        telegramLayout.setOnClickListener {
            onClickListener?.onClickListener(RequestCreateBot.TELEGRAM)
            dismiss()
        }
        vkLayout.setOnClickListener {
            onClickListener?.onClickListener(RequestCreateBot.VK)
            dismiss()
        }
        viberLayout.setOnClickListener {
            onClickListener?.onClickListener(RequestCreateBot.VIBER)
            dismiss()
        }

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

    interface OnMessengerClickListener {
        fun onClickListener(messengerName: String)

    }
}
