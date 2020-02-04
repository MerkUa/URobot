package com.urobot.droid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.urobot.droid.R
import com.urobot.droid.data.model.GetAllIndustryModel

class IndustryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val allIndusty: ArrayList<GetAllIndustryModel> =
        mutableListOf<GetAllIndustryModel>() as ArrayList<GetAllIndustryModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val theView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_industry, parent, false)
        return IndustryViewHolder(theView)
    }

    override fun getItemCount(): Int {
     return  1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }


    inner class IndustryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init { }
    }
}