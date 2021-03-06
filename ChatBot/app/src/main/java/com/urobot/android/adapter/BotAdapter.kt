package com.urobot.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.urobot.android.R
import com.urobot.android.data.model.BotContentItem
import com.urobot.android.data.model.BotData
import com.urobot.android.data.model.ServiceButtons
import kotlinx.android.synthetic.main.horizontal_layout_home.view.*

class HomeBotAdapter(
    private val activity: AppCompatActivity
) :
    RecyclerView.Adapter<HomeBotAdapter.HomeBotViewHolder>() {

    private var horizontalAdapter: ContentBotAdapter? = null
    private val recycledViewPool: RecycledViewPool = RecycledViewPool()

    private val data: ArrayList<BotData> = mutableListOf<BotData>() as ArrayList<BotData>
    var id: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBotViewHolder {
        val theView =
            LayoutInflater.from(activity).inflate(R.layout.horizontal_layout_home, parent, false)
        return HomeBotViewHolder(theView)
    }

    override fun onBindViewHolder(holder: HomeBotViewHolder, position: Int) {
        val level = (holder.adapterPosition + 1)
        var description = ""
        if (level == 1) {
            description = holder.itemView.context.getString(R.string.level1_description)
        } else if (level == 2) {
            description = holder.itemView.context.getString(R.string.level2_description)
        }
        holder.itemView.tv_level.text =
            holder.itemView.context.getString(R.string.n_level, level.toString()) + description

        horizontalAdapter =
            ContentBotAdapter(data[position].botContentList, activity)
        horizontalAdapter!!.setbot(id)
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
        val listContent: ArrayList<BotContentItem> = ArrayList()
        for (dataList in data) {
            for (dataItem in dataList.botContentList) {

                if (dataItem.id!! == bot.id) {
                    var positionOfItem = dataList.botContentList.indexOf(dataItem)
                    dataList.botContentList[positionOfItem] = bot

                    val list: ArrayList<ServiceButtons>? = ArrayList()


                    if ((data.indexOf(dataList) + 1) == data.size) {
                        for (buttons in bot.list_buttons!!) {
//                            Log.d("addData","addData "+(data.size + bot.id!! + buttons.id!!))
                            listContent.add(
                                BotContentItem(
                                    bot.parent_id!! + buttons.id!!,
                                    bot.id,
                                    (data.indexOf(dataList) + 2),
                                    null,
                                    buttons.id,
                                    true,
                                    "",
                                    buttons.name,
                                    buttons.id.toString(),
                                    list
                                )
                            )
                        }
                    } else {
                        val nextRow = data[(data.indexOf(dataList) + 1)]
                        for (buttons in bot.list_buttons!!) {
                            val listId: ArrayList<String> = ArrayList()
                            for (service in nextRow.botContentList) {
                                listId.add(service.id.toString())
                            }
//                            Log.d("addData","addData "+(data.size + bot.id!! + buttons.id!!))
                            val content = BotContentItem(
                                bot.parent_id!! + buttons.id!!,
                                bot.id,
                                (data.indexOf(dataList) + 2),
                                null,
                                buttons.id,
                                true,
                                "",
                                buttons.name,
                                buttons.id.toString(),
                                list
                            )

                            if (!listId.contains((bot.parent_id!! + buttons.id!!).toString())) {
                                nextRow.botContentList.add(content)
                            }

                        }
                    }
                }
            }
        }
        if (listContent.isNotEmpty()) {
            data.add(BotData(listContent))
        }
        notifyDataSetChanged()
    }


    fun setData(items : ArrayList<BotData>){
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun setBotId(botId: String) {
        id = botId
    }
}
