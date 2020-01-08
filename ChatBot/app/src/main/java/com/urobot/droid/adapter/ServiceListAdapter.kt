package com.urobot.droid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.urobot.droid.R
import com.urobot.droid.data.model.Service

class ServiceListAdapter() : RecyclerView.Adapter<ServiceListAdapter.MyItemViewHolder>() {


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private val dataSource: ArrayList<Service> = mutableListOf<Service>() as ArrayList<Service>

    fun setData(items: ArrayList<Service>) {
        dataSource.clear()
        dataSource.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_services, parent, false)
        val viewHolder = MyItemViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    override fun onBindViewHolder(holder: MyItemViewHolder, position: Int) {
        holder.title.text = dataSource[position].title
        holder.descriptoin.text = dataSource[position].description

    }

    inner class MyItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var title: TextView
        var descriptoin: TextView


        init {
            title = itemView.findViewById(R.id.titleService)
            descriptoin = itemView.findViewById(R.id.descriptionService)
        }

        override fun onClick(v: View?) {
        }
    }

}