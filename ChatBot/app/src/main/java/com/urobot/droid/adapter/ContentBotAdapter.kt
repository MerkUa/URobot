package com.urobot.droid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.urobot.droid.R
import com.urobot.droid.data.model.BotData
import com.urobot.droid.ui.dialogs.CreateEventDialogFragment
import kotlinx.android.synthetic.main.item_with_event_create_bot.view.*


class ContentBotAdapter( private var botAdapterPosition: Int, private val botList: ArrayList<BotData> ,private val context: Context) :
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

        val lastPosition =  itemCount - 1

        return when {

            (botAdapterPosition == 0) and (botList.size == 0)  -> {
                emptyType

            }

            (position == lastPosition) and (lastPosition != 0)-> {
                emptyType

            }

            (botAdapterPosition > 0) and (botList.size > 0) and (position == 0) -> {
                contentType
            }

            else -> {
                contentType
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        //TODO crash

//        if((getItemViewType(position) == contentType) and botList.isNotEmpty()){




//                holder.itemView.text_from_dialog.text = botList[holder.adapterPosition].botContentList[holder.adapterPosition].description
//
//                val listButtons = botList[position].botContentList[position].list_buttons
//
//            if (listButtons != null) {
//                for( item in listButtons){
//                    if(item.id == 1){
//                        holder.itemView.write_to_event.visibility = View.VISIBLE
//                    }else{
//                        holder.itemView.payment_button.visibility = View.VISIBLE
//                    }
//                }
//            }
//            }

    }

    override fun getItemCount(): Int {
        return when {
            botAdapterPosition == 0 -> {
                1
            }
            botList.size==1 -> {
                botList.size + 1
            }
            else -> {
                botList.size + 1
            }
        }
    }

    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        
        init{

        }
    }

    inner class AddContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            val manager = (context as AppCompatActivity).supportFragmentManager
            context.supportFragmentManager.beginTransaction()
            val newFragment = CreateEventDialogFragment.getInstance()
            newFragment?.show(manager, "dialog")
        }
    }
}