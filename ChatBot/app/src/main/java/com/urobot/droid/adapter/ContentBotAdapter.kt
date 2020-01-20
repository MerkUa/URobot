package com.urobot.droid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.urobot.droid.R
import com.urobot.droid.data.model.BotContentItem
import com.urobot.droid.ui.createbot.CreateBotActivity
import com.urobot.droid.ui.dialogs.CreateEventDialogFragment
import kotlinx.android.synthetic.main.item_with_event_create_bot.view.*


class ContentBotAdapter(
    private var botAdapterPosition: Int,
    private val botList: List<BotContentItem>,
    private val activity: AppCompatActivity
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val emptyType = 0
    private val contentType = 1

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

            (botList[position].isEmpty) -> {
                emptyType
            }
            else -> {
                contentType
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if ((getItemViewType(position) == contentType) and botList.isNotEmpty()) {

            holder.itemView.text_from_dialog.text = botList[position].description

            val listButtons = botList[position].list_buttons

            if (listButtons != null) {
                for (item in listButtons) {
                    if (item.id == 1) {
                        holder.itemView.write_to_event.visibility = View.VISIBLE
                    } else {
                        holder.itemView.payment_button.visibility = View.VISIBLE
                    }
                }
            }

        }
        if (getItemViewType(position) == emptyType) {
            holder.itemView.setOnClickListener {
                val manager = activity.supportFragmentManager
                activity.supportFragmentManager.beginTransaction()
                val newFragment = CreateEventDialogFragment.getInstance(botList[position])
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

    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {

        }
    }

    inner class AddContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        init {

        }


    }
}