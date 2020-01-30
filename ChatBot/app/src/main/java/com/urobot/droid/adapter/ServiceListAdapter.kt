package com.urobot.droid.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.urobot.droid.R
import com.urobot.droid.data.model.*
import kotlinx.android.synthetic.main.list_item_calendar_services.view.*
import kotlinx.android.synthetic.main.list_item_payment_services.view.*

class ServiceListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val allDataServices: ArrayList<GetAllServicesModel> = mutableListOf<GetAllServicesModel>() as ArrayList<GetAllServicesModel>

    private val onlineRecordType = 0
    private val paymentType = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if(viewType == onlineRecordType) {
            val onlineRecordView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_calendar_services, parent,false)
            OnlineRecordViewHolder(onlineRecordView)
        } else {
            val paymentView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_payment_services, parent,false)
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

    if(getItemViewType(position) == onlineRecordType){

        val temp: List<Datum>?  = allDataServices[position].data

        for( item in temp!!){
            Log.d("adapter", "temp size"+temp.size.toString())
            holder.itemView.titleCalendarService.text = item.name
        }

          holder.itemView.updateCalendarService.setOnClickListener {

      }
}

        if(getItemViewType(position) == paymentType){

        val temp:List<Datum>? = allDataServices[position].data

            for(item in temp!!){
                holder.itemView.descriptionPaymentService.text = item.cvv
            }
        }
    }


    override fun getItemCount(): Int {

        Log.d("adapter", "initSize: " + allDataServices.size.toString())
        return allDataServices.size
    }


    inner class PaymentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init { }

    }

    inner class OnlineRecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init { }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun addData(data : List<GetAllServicesModel>){
        allDataServices.clear()
        allDataServices.addAll(data)
        notifyDataSetChanged()
        Log.d("adapter", "allData size: " + allDataServices.size.toString())
    }
}