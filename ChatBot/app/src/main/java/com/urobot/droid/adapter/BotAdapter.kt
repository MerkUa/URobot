package com.urobot.droid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.urobot.droid.R
import com.urobot.droid.data.model.BotData
import com.urobot.droid.data.model.Contact
import kotlinx.android.synthetic.main.horizontal_layout_home.view.*

class HomeBotAdapter(
    private val context: Context) :
    RecyclerView.Adapter<HomeBotAdapter.HomeBotViewHolder>() {

    private var horizontalAdapter: ContentBotAdapter? = null
    private val recycledViewPool: RecycledViewPool = RecycledViewPool()

    private val data: ArrayList<BotData> = mutableListOf<BotData>() as ArrayList<BotData>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBotViewHolder {
        val theView =
            LayoutInflater.from(context).inflate(R.layout.horizontal_layout_home, parent, false)
        return HomeBotViewHolder(theView)
    }

    override fun onBindViewHolder(holder: HomeBotViewHolder, position: Int) {
        val level = (holder.adapterPosition + 1)
        holder.itemView.tv_level.text =  ("$level Уровень")

        val botAdapterPosition = position

        horizontalAdapter = ContentBotAdapter(botAdapterPosition, data, context)
        holder.itemView.home_recycler_view_horizontal.adapter = horizontalAdapter
        holder.itemView.home_recycler_view_horizontal.setRecycledViewPool(recycledViewPool)
        holder.itemView.home_recycler_view_horizontal.setHasFixedSize(true)

    }

    override fun getItemCount(): Int {
        return when {
            data.isEmpty() -> {
                1
            }
            data.size == 1 -> {
                data.size + 1
            }
            else -> {
                data.size + 1
            }
        }
    }

     class HomeBotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val horizontalManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

        init {
            itemView.home_recycler_view_horizontal.setHasFixedSize(true)
            itemView.home_recycler_view_horizontal.isNestedScrollingEnabled = false
            itemView.home_recycler_view_horizontal.layoutManager = horizontalManager
            itemView.home_recycler_view_horizontal.itemAnimator = DefaultItemAnimator()
        }
    }


    fun setData(items : ArrayList<BotData>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }
}
