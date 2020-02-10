package com.urobot.droid.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.urobot.droid.R
import com.urobot.droid.data.model.BotContentItem
import com.urobot.droid.data.model.ServiceButtons
import kotlinx.android.synthetic.main.dialog_fragment_create_event.*


class CreateEventDialogFragment : DialogFragment(), View.OnClickListener,
    AddButtonBottomSheetDialog.AddButtonBottomSheetListener {

    private var addButtonDialog = AddButtonBottomSheetDialog()
    private var changeDataListener: ChangeDataListener? = null
    private var list: ArrayList<ServiceButtons>? = ArrayList()
    private var botContentItem: BotContentItem? = null

    companion object {
        private var instanceFragment: CreateEventDialogFragment? = null

        fun newInstance(botItem: BotContentItem): CreateEventDialogFragment? {
            instanceFragment = null
            instanceFragment =
                CreateEventDialogFragment()
            instanceFragment!!.setContentItem(botItem)
            return instanceFragment
        }
    }

    private fun setContentItem(botItem: BotContentItem) {
        botContentItem = botItem
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.dialog_fragment_create_event, null)

        view.findViewById<View>(R.id.add_buttons_tv).setOnClickListener(this)
        view.findViewById<TextView>(R.id.save_button).setOnClickListener(this)

        addButtonDialog.setSelectedListener(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        descriptionEditText.setText(botContentItem!!.description)
        for (button in botContentItem!!.list_buttons!!) {

            val itemView = LinearLayout.inflate(
                context,
                R.layout.item_botton,
                null
            )
            itemView.findViewById<TextView>(R.id.payment_button_dialog_fragment)
                .setText(button.name)
            linearLayout.addView(itemView)

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

    override fun onClick(v: View) {
        when (v.id) {
            R.id.add_buttons_tv -> {
                val bottomSheetFragment = context?.let { addButtonDialog }
                val manager = (context as AppCompatActivity).supportFragmentManager
                bottomSheetFragment?.show(manager, "dialog")
            }
            R.id.save_button -> {
                changeDataListener.let {
                    botContentItem!!.isEmpty = false
                    botContentItem!!.description = descriptionEditText.text.toString()
                    botContentItem!!.list_buttons = list

                    if (botContentItem!!.parent_id != null) {
                        botContentItem!!.parent_id = botContentItem!!.id
                    }


                    it!!.onBotDataChanged(botContentItem!!)
                }
                descriptionEditText.setText("")
//                list?.clear()
                dismiss()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        instanceFragment = null
        super.onDismiss(dialog)
    }

    override fun ButtonClick(button: ServiceButtons) {
        Log.d("ButtonClick", "list " + list!!.size)

        when {
            button.id == 1.toLong() -> {
                if (!list!!.contains(ServiceButtons(1, null))) {
                    val itemView = LinearLayout.inflate(
                        context,
                        R.layout.item_botton,
                        null
                    )
                    itemView.findViewById<TextView>(R.id.payment_button_dialog_fragment)
                        .setText(getString(R.string.write_event_and_important))
                    linearLayout.addView(itemView)
                    list?.add(ServiceButtons(1, getString(R.string.write_event_and_important)))
                }
            }
            button.id == 2.toLong() -> {
                if (!list!!.contains(ServiceButtons(2, null))) {
                    val itemView = LinearLayout.inflate(
                        context,
                        R.layout.item_botton,
                        null
                    )
                    itemView.findViewById<TextView>(R.id.payment_button_dialog_fragment)
                        .setText(getString(R.string.payment))
                    linearLayout.addView(itemView)
                    list?.add(ServiceButtons(2, getString(R.string.payment)))

                }
            }
            button.id == 3.toLong() -> {
                showDialog()
            }

        }
    }

    fun showDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Новая кнопка")

        val view = layoutInflater.inflate(R.layout.dialog_edit_text, null)

        val categoryEditText = view.findViewById(R.id.categoryEditText) as EditText

        builder.setView(view);

        // set up the ok button
        builder.setPositiveButton(android.R.string.ok) { dialog, p1 ->
            if (categoryEditText.text.isNotEmpty()) {
                val itemView = LinearLayout.inflate(
                    context,
                    R.layout.item_botton,
                    null
                )
                itemView.findViewById<TextView>(R.id.payment_button_dialog_fragment)
                    .setText(categoryEditText.text)
                linearLayout.addView(itemView)
                list?.add(
                    ServiceButtons(
                        System.currentTimeMillis(),
                        categoryEditText.text.toString()
                    )
                )
                dialog.cancel()
            }
        }

        builder.setNegativeButton(android.R.string.cancel) { dialog, p1 ->
            dialog.cancel()
        }

        builder.show()
    }

    interface ChangeDataListener {
        fun onBotDataCreated(botContentItem: BotContentItem)
        fun onBotDataChanged(botContentItem: BotContentItem)
    }

    fun setSelectedListener(listener: ChangeDataListener) {
        this.changeDataListener = listener
    }
}