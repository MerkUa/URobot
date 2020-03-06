package com.urobot.droid.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.urobot.droid.R
import com.urobot.droid.db.Messenger
import kotlinx.android.synthetic.main.dialog_fragment_choose_messenger.*


class ChooseMessengerDialogFragment : DialogFragment() {

    private var onClickListener: OnMessengerClickListener? = null
    var messengerId: Int = 0
    private var robotId = ""

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
            linearLayoutMessengers.visibility = View.GONE
            linearLayoutText.visibility = View.VISIBLE
            messengerId = Messenger.Facebook.messengerId
        }
        telegramLayout.setOnClickListener {
            linearLayoutMessengers.visibility = View.GONE
            linearLayoutText.visibility = View.VISIBLE
            messengerId = Messenger.Telegram.messengerId
        }
        vkLayout.setOnClickListener {
            linearLayoutMessengers.visibility = View.GONE
            linearLayoutText.visibility = View.VISIBLE
            codeLayout.visibility = View.VISIBLE
            messengerId = Messenger.Vk.messengerId
        }
        viberLayout.setOnClickListener {
            linearLayoutMessengers.visibility = View.GONE
            linearLayoutText.visibility = View.VISIBLE
            messengerId = Messenger.Viber.messengerId
        }
        addBotButton.setOnClickListener {
            onClickListener?.onClickListener(
                robotId,
                messengerId,
                tokenEditText.text.toString(),
                codeEditText.text.toString()
            )
            dismiss()
        }

    }

    fun setSelectedListener(listener: OnMessengerClickListener) {
        this.onClickListener = listener
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog.window?.setLayout(width, height)
        }
    }

    fun setRobotId(botId: String) {
        robotId = botId
    }

    interface OnMessengerClickListener {
        fun onClickListener(robotId: String, messengerId: Int, token: String, code: String)

    }
}
