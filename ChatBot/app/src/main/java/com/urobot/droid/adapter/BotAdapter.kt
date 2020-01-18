package com.urobot.droid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.urobot.droid.R
import com.urobot.droid.data.model.BotContentItem
import com.urobot.droid.data.model.BotData
import kotlinx.android.synthetic.main.horizontal_layout_home.view.*

class HomeBotAdapter(
    private val activity: AppCompatActivity
) :
    RecyclerView.Adapter<HomeBotAdapter.HomeBotViewHolder>() {

    private var horizontalAdapter: ContentBotAdapter? = null
    private val recycledViewPool: RecycledViewPool = RecycledViewPool()

    private val data: ArrayList<BotData> = mutableListOf<BotData>() as ArrayList<BotData>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBotViewHolder {
        val theView =
            LayoutInflater.from(activity).inflate(R.layout.horizontal_layout_home, parent, false)
        return HomeBotViewHolder(theView)
    }

    override fun onBindViewHolder(holder: HomeBotViewHolder, position: Int) {
        val level = (holder.adapterPosition + 1)
        holder.itemView.tv_level.text =  ("$level Уровень")

        val botAdapterPosition = position

        horizontalAdapter =
            ContentBotAdapter(botAdapterPosition, data[position].botContentList, activity)
        holder.itemView.home_recycler_view_horizontal.adapter = horizontalAdapter
        holder.itemView.home_recycler_view_horizontal.setRecycledViewPool(recycledViewPool)
        holder.itemView.home_recycler_view_horizontal.setHasFixedSize(true)

    }

    override fun getItemCount(): Int {
        return data.size
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

    fun addData(bot: BotContentItem) {
        for (dataList in data) {
            for (dataItem in dataList.botContentList) {
                if (dataItem.id!! == bot.id) {
                    var positionOfItem = dataList.botContentList.indexOf(dataItem)
                    dataList.botContentList.set(positionOfItem, bot)

//                    var positionORow = data.indexOf(dataList)
//
//                    data[positionORow + 1]

                }
            }
        }
        notifyDataSetChanged()
    }


    fun setData(items : ArrayList<BotData>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }
}
