package com.urobot.droid.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.urobot.droid.R
import com.urobot.droid.data.model.GetAllServicesModel
import kotlinx.android.synthetic.main.item_botton_indialog.view.*
import kotlinx.android.synthetic.main.list_item_calendar_services.view.*

class ServiceButtonsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var services: ArrayList<GetAllServicesModel> =
        mutableListOf<GetAllServicesModel>() as ArrayList<GetAllServicesModel>
    private var mOnButtonClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val paymentView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_botton_indialog, parent, false)
        return ButtonViewHolder(paymentView)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val decription: String? = services[position].name


        if (decription != null) {
            holder.itemView.button.text = decription
            Log.d("Merk", "decription " + decription)

        } else {
            holder.itemView.titleCalendarService.text = "!!!!!!!"
        }

        holder.itemView.button.setOnClickListener(View.OnClickListener { view ->
            mOnButtonClickListener?.onItemClick(
                view, services.get(position)
            )
        })
    }


    override fun getItemCount(): Int {
        return services.size
    }


    inner class ButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
        }

    }

    interface ItemClickListener {
        fun onItemClick(view: View?, item: GetAllServicesModel)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addData(data: List<GetAllServicesModel>) {
        Log.d("Merk", "data " + data.size)

        services.clear()
        services.addAll(data)
        notifyDataSetChanged()
    }

    fun addClickListener(itemClickListener: ItemClickListener) {
        mOnButtonClickListener = itemClickListener
    }
}