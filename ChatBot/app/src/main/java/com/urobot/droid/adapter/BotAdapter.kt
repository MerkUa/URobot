package com.urobot.droid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.urobot.droid.R
import com.urobot.droid.data.model.BotData

class HomeBotAdapter(
    private val data: List<BotData>,
    private val context: Context
) :
    RecyclerView.Adapter<HomeBotAdapter.HomeBotViewHolder>() {
    private var horizontalAdapter: SubBotAdapter? = null
    private val recycledViewPool: RecycledViewPool
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBotViewHolder {
        val theView =
            LayoutInflater.from(context).inflate(R.layout.row_layout_home, parent, false)
        return HomeBotViewHolder(theView)
    }

    override fun onBindViewHolder(holder: HomeBotViewHolder, position: Int) {
        holder.textViewCategory.text = data[position].level
        horizontalAdapter = SubBotAdapter(data[position].list, context)
        holder.recyclerViewHorizontal.adapter = horizontalAdapter
        holder.recyclerViewHorizontal.setRecycledViewPool(recycledViewPool)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class HomeBotViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        internal val recyclerViewHorizontal: RecyclerView
        internal val textViewCategory: TextView
        internal val horizontalManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        init {
            recyclerViewHorizontal = itemView.findViewById(R.id.home_recycler_view_horizontal)
            recyclerViewHorizontal.setHasFixedSize(true)
            recyclerViewHorizontal.isNestedScrollingEnabled = false
            recyclerViewHorizontal.layoutManager = horizontalManager
            recyclerViewHorizontal.itemAnimator = DefaultItemAnimator()
            textViewCategory = itemView.findViewById(R.id.tv_movie_category)
        }
    }

    init {
        recycledViewPool = RecycledViewPool()
    }
}
