package com.urobot.android.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.urobot.android.R
import com.urobot.android.data.model.GetAllIndustryModel
import com.urobot.android.data.model.IdsModel
import com.urobot.android.db.Industry
import kotlinx.android.synthetic.main.item_industry.view.*

class IndustryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val allIndustryFromServer: ArrayList<GetAllIndustryModel> = mutableListOf<GetAllIndustryModel>() as ArrayList<GetAllIndustryModel>
    private val allIndustryFromLocalDB: ArrayList<Industry> = mutableListOf<Industry>() as ArrayList<Industry>
    private val updateListIndustry: ArrayList<IdsModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val theView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_industry, parent, false)
        return IndustryViewHolder(theView)
    }

    override fun getItemCount(): Int {
     return allIndustryFromServer.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.nameIndustry.text = allIndustryFromServer[position].name

        for (item in allIndustryFromLocalDB){
            if(item.id == allIndustryFromServer[position].id.toString()){
                holder.itemView.checkboxIndustry.isChecked = true
            }
        }
    }

    inner class IndustryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){


        init {
           itemView.checkboxIndustry.setOnCheckedChangeListener { buttonView, isChecked ->

               if(buttonView.isChecked){

                   updateListIndustry.add(IdsModel(allIndustryFromServer[adapterPosition].id.toString()))
                   Log.d("list", updateListIndustry.size.toString())
                   Log.d("list ", updateListIndustry.toString())

               }else{
                   updateListIndustry.remove(IdsModel(allIndustryFromServer[adapterPosition].id.toString()))
               }



           }
        }

    }

    fun addDataFromServer(data: List<GetAllIndustryModel>) {
        allIndustryFromServer.clear()
        allIndustryFromServer.addAll(data)
        notifyDataSetChanged()
        Log.d("IndustryAdapterServer", allIndustryFromServer.size.toString())
    }

    fun addDataFromLocalDB(data: List<Industry>) {
        allIndustryFromLocalDB.clear()
        allIndustryFromLocalDB.addAll(data)
        notifyDataSetChanged()
        Log.d("IndustryAdapterLocalDB", allIndustryFromLocalDB.size.toString())
    }
    fun getUpdateIndustryData(): ArrayList<IdsModel> {
        return updateListIndustry
    }
}