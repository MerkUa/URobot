package com.urobot.droid.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.urobot.droid.R
import com.urobot.droid.data.model.Datum
import com.urobot.droid.data.model.GetAllServicesModel
import com.urobot.droid.data.model.TypeServices
import kotlinx.android.synthetic.main.list_item_calendar_services.view.*
import kotlinx.android.synthetic.main.list_item_payment_services.view.*

class ServiceListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val allDataServices: ArrayList<GetAllServicesModel> =
        mutableListOf<GetAllServicesModel>() as ArrayList<GetAllServicesModel>

    private val onlineRecordType = 0
    private val paymentType = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == onlineRecordType) {
            val onlineRecordView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_calendar_services, parent, false)
            OnlineRecordViewHolder(onlineRecordView)
        } else {
            val paymentView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_payment_services, parent, false)
            PaymentViewHolder(paymentView)
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if (allDataServices[position].typeId == TypeServices.onlineRecord.type_id) {
            onlineRecordType
        } else {
            paymentType
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == onlineRecordType) {

            val temp: Datum? = allDataServices[position].data

            if (temp != null) {
                holder.itemView.titleCalendarService.text = temp.name
                Log.d("Merk", "getItemViewType " + temp.name)

            } else {
                holder.itemView.titleCalendarService.text = "!!!!!!!"
            }

            holder.itemView.updateCalendarService.setOnClickListener {

            }
        }

        if (getItemViewType(position) == paymentType) {
            val temp: Datum? = allDataServices[position].data
            if (temp != null) {
                holder.itemView.descriptionPaymentService.text = "!"
                holder.itemView.descriptionPaymentService.text = temp.cvv
            }
        }
    }


    override fun getItemCount(): Int {
        return allDataServices.size
    }


    inner class PaymentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
        }

    }

    inner class OnlineRecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addData(data: List<GetAllServicesModel>) {
        Log.d("Merk", "data " + data.size)

        allDataServices.clear()
        allDataServices.addAll(data)
        notifyDataSetChanged()
    }
}