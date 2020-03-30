package com.urobot.droid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.urobot.droid.R
import com.urobot.droid.data.model.ServiceButtons
import com.urobot.droid.ui.dialogs.CreateEventDialogFragment
import kotlinx.android.synthetic.main.list_item_create_event.view.*

class ButtonsListAdapter : RecyclerView.Adapter<ButtonsListAdapter.ButtonsViewHolder>() {

    private val listButtons: ArrayList<ServiceButtons> =
        mutableListOf<ServiceButtons>() as ArrayList<ServiceButtons>
    private var mOnButtonClickListener: ItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_create_event, parent, false)
        return ButtonsViewHolder(view)

    }

    override fun getItemCount(): Int {
        return listButtons.size
    }

    fun getItemByPosirion(position: Int): ServiceButtons {
        return listButtons[position]
    }

    override fun onBindViewHolder(holder: ButtonsViewHolder, position: Int) {
        holder.bind()
    }

    inner class ButtonsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            itemView.nameButton.text =
                (adapterPosition + 1).toString() + ". " + listButtons[adapterPosition].name

            itemView.nameButton.setOnClickListener(View.OnClickListener { view ->
                mOnButtonClickListener?.onItemClick(
                    view, adapterPosition
                )
            })
        }
    }

    fun addData(data: List<ServiceButtons>) {
        listButtons.clear()
        listButtons.addAll(data)
        notifyDataSetChanged()
    }

    fun addButton(data: ServiceButtons) {
        listButtons.add(data)
        notifyDataSetChanged()
    }

    fun addClickListener(itemClickListener: CreateEventDialogFragment) {
        mOnButtonClickListener = itemClickListener
    }

    fun getItems(): ArrayList<ServiceButtons>? {
        return listButtons
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, item: Int)
    }

}