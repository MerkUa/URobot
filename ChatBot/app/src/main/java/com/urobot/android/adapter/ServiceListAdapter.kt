package com.urobot.android.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.urobot.android.R
import com.urobot.android.data.model.GetAllServicesModel
import com.urobot.android.data.model.TypeServices
import kotlinx.android.synthetic.main.list_item_calendar_services.view.*
import kotlinx.android.synthetic.main.list_item_payment_services.view.*

class ServiceListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val allDataServices: ArrayList<GetAllServicesModel> =
        mutableListOf<GetAllServicesModel>() as ArrayList<GetAllServicesModel>

    private val onlineRecordType = 0
    private val paymentType = 1
    private var mOnServiceClickListener: ItemClickListener? = null


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

            val temp = allDataServices[position]


            if (temp != null) {
                holder.itemView.titleCalendarService.text = allDataServices[position].name
                Log.d("Merk", "getItemViewType " + temp.name)

            } else {
                holder.itemView.titleCalendarService.text = "!!!!!!!"
            }

            holder.itemView.updateCalendarService.setOnClickListener {
                //                val id = allDataServices[position].id
//
//                val action = RobotFragmentDirections.actionNavigationUbotToNavigationCreateCalendar().setCalendarData(temp).setServiceId(id!!)
//                holder.itemView.findNavController().navigate(action)
                mOnServiceClickListener?.onChangeCalendarClick(temp)

            }
        }

        if (getItemViewType(position) == paymentType) {
            val temp = allDataServices[position]
            holder.itemView.titlePaymentService.text = allDataServices[position].name
            holder.itemView.descriptionPaymentService.text = allDataServices[position].description
            holder.itemView.updatePaymentService.setOnClickListener {
                //                mOnServiceClickListener?.onChangeClick(temp)

//                val id = allDataServices[position].id
//
//                    val action = RobotFragmentDirections.actionNavigationUbotToNavigationCreatePayment().setPaymentData(temp).setServiceId(id!!)
//                    holder.itemView.findNavController().navigate(action)

                mOnServiceClickListener?.onChangePaymentClick(temp)
                }
//            val temp: Datum? = allDataServices[position].data
//            if (temp != null) {
//                holder.itemView.titlePaymentService.text = temp.name
//                holder.itemView.descriptionPaymentService.text = "!"
//                holder.itemView.descriptionPaymentService.text = temp.cvv
//
//                holder.itemView.updatePaymentService.setOnClickListener {
//                    val id = allDataServices[position].id
//
//                    val action = RobotFragmentDirections.actionNavigationUbotToNavigationCreatePayment().setPaymentData(temp).setServiceId(id!!)
//                    holder.itemView.findNavController().navigate(action)
//                }
//            }
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

    interface ItemClickListener {
        fun onChangePaymentClick(temp: GetAllServicesModel)
        fun onChangeCalendarClick(temp: GetAllServicesModel)

    }

    fun addClickListener(itemClickListener: ItemClickListener) {
        mOnServiceClickListener = itemClickListener
    }
}