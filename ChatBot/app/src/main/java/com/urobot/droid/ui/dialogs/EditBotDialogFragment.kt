package com.urobot.droid.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.urobot.droid.R
import com.urobot.droid.data.model.Bot
import kotlinx.android.synthetic.main.create_bot_fragment.*


class EditBotDialogFragment : DialogFragment() {

    private var onClickListener: OnEditClickListener? = null
    private var robot: Bot? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.create_bot_fragment, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createBtn.visibility = View.GONE
        updateBtn.visibility = View.VISIBLE
        deleteBtn.visibility = View.VISIBLE
        nameBotEditText.setText(robot?.title)
        descriptionBotEditText.setText(robot?.description)
        updateBtn.setOnClickListener {
            onClickListener?.onEditListener(
                robot!!.botId,
                nameBotEditText.text.toString(),
                descriptionBotEditText.text.toString()
            )
            dismiss()
        }
        deleteBtn.setOnClickListener {
            onClickListener?.onDeleteListener(robot!!.botId)
            dismiss()
        }

    }

    fun setSelectedListener(listener: OnEditClickListener) {
        this.onClickListener = listener
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


    fun setRobot(bot: Bot) {
        robot = bot
    }

    interface OnEditClickListener {
        fun onEditListener(robotId: String, title: String, description: String)
        fun onDeleteListener(robotId: String)

    }
}
