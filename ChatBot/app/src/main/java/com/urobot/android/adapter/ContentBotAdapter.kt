package com.urobot.android.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.urobot.android.R
import com.urobot.android.data.model.BotContentItem
import com.urobot.android.ui.createbot.CreateBotActivity
import com.urobot.android.ui.dialogs.CreateEventDialogFragment
import kotlinx.android.synthetic.main.item_with_event_create_bot.view.*


class ContentBotAdapter(
    private val botList: List<BotContentItem>,
    private val activity: AppCompatActivity
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val emptyType = 0
    private val contentType = 1
    private var botId = ""


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if(viewType == emptyType) {
            val emptyView = LayoutInflater.from(parent.context).inflate(R.layout.item_empty_type_create_bot, parent,false)
            AddContentViewHolder(emptyView)
        } else {
            val contentView = LayoutInflater.from(parent.context).inflate(R.layout.item_with_event_create_bot, parent,false)

            ContentViewHolder(contentView)
        }
    }

    override fun getItemViewType(position: Int): Int {

        return when {

            (botList[position].description?.isNotEmpty() ?: !botList[position].isEmpty) -> {
                contentType
            }
            else -> {
                emptyType
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if ((getItemViewType(position) == contentType) and botList.isNotEmpty()) {

            holder.itemView.text_from_dialog.text = botList[position].description

            holder.itemView.textId.text = botList[position].level.toString() + "." + (position + 1)

            val listButtons = botList[position].list_buttons

            if (listButtons != null && listButtons.size > 0) {
                holder.itemView.buttonsLinearLayout.visibility = View.VISIBLE
                holder.itemView.buttonsLinearLayout.removeAllViews()
                for (item in listButtons) {
                    val itemView = LinearLayout.inflate(
                        holder.itemView.context,
                        R.layout.item_botton,
                        null
                    )
                    itemView.findViewById<TextView>(R.id.payment_button_dialog_fragment)
                        .setText(item.name)
                    holder.itemView.buttonsLinearLayout.addView(itemView)

                }
            } else {
                holder.itemView.buttonsLinearLayout.visibility = View.INVISIBLE
            }

            holder.itemView.setOnClickListener {
                holder.itemView.setOnClickListener {
                    val manager = activity.supportFragmentManager
                    activity.supportFragmentManager.beginTransaction()
                    val newFragment =
                        CreateEventDialogFragment.newInstance(botList[position], botId)
                    newFragment?.setSelectedListener(activity as CreateBotActivity)
                    newFragment?.show(manager, botList[position].id.toString())
                    holder.itemView.text_from_dialog.text = botList[position].description

                }
            }
        }
        if (getItemViewType(position) == emptyType) {
            holder.itemView.setOnClickListener {
                val manager = activity.supportFragmentManager
                activity.supportFragmentManager.beginTransaction()
                Log.d("newInstance", "action " + botList[position].action)
                val newFragment = CreateEventDialogFragment.newInstance(botList[position], botId)
                newFragment?.setSelectedListener(activity as CreateBotActivity)
                newFragment?.show(manager, botList[position].id.toString())
            }
        }

    }

    override fun getItemCount(): Int {
//        return when {
//            botAdapterPosition == 0 -> {
//                1
//            }
//            botList.size==1 -> {
//                botList.size + 1
//            }
//            else -> {
//                botList.size + 1
//            }
//        }
        return botList.size
    }

    fun setbot(id: String) {
        botId = id
    }

    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {

        }
    }

    inner class AddContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        init {

        }


    }
}