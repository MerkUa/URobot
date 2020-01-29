package com.urobot.droid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.urobot.droid.R
import com.urobot.droid.data.model.GetAllServicesModel
import com.urobot.droid.data.model.OnlineRecordModel
import com.urobot.droid.data.model.PaymentModel
import com.urobot.droid.data.model.TypeServices
import com.urobot.droid.ui.dialogs.BottomCalendarFragment
import kotlinx.android.synthetic.main.list_item_calendar_services.view.*

class ServiceListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val activity = AppCompatActivity()

    private val dataOnlineRecordModel: ArrayList<OnlineRecordModel> = mutableListOf<OnlineRecordModel>() as ArrayList<OnlineRecordModel>
    private val dataPaymentModel: ArrayList<PaymentModel> = mutableListOf<PaymentModel>() as ArrayList<PaymentModel>
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

        return when {

            (allDataServices[position].typeId == TypeServices.onlineRecord.type_id) -> {
                onlineRecordType
            }
            else -> {
                paymentType
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    if(getItemViewType(position) == onlineRecordType){
        holder.itemView.titleCalendarService.text = dataOnlineRecordModel[position].name
        holder.itemView.descriptionCalendarService.text = dataOnlineRecordModel[position].working_days[position]

        holder.itemView.updateCalendarService.setOnClickListener {
            val manager = activity.supportFragmentManager
            activity.supportFragmentManager.beginTransaction()
//            val newFragment = BottomCalendarFragment.newInstance(botList[position])
//            newFragment?.setSelectedListener(activity as CreateBotActivity)
//            newFragment?.show(manager, dataOnlineRecordModel[position])
        }
    }
    }


    override fun getItemCount(): Int {
        return dataOnlineRecordModel.size + dataPaymentModel.size
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

    fun setDataOnlineRecord(items: ArrayList<OnlineRecordModel>) {
        dataOnlineRecordModel.clear()
        dataOnlineRecordModel.addAll(items)
        notifyDataSetChanged()
    }

    fun setDataPaymentService(items: ArrayList<PaymentModel>){
        dataPaymentModel.clear()
        dataPaymentModel.addAll(items)
        notifyDataSetChanged()
    }

    fun addData(data : List<GetAllServicesModel>){
        allDataServices.clear()
        allDataServices.addAll(data)
        notifyDataSetChanged()
    }
}