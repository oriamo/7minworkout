package com.example.a7minworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minworkout.databinding.HistoryItemBinding

class HistoryAdapter( private val items : ArrayList<HistoryEntity>,private val deleteListener: (id: Int) -> Unit ): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    inner class ViewHolder( val itemBinding: HistoryItemBinding): RecyclerView.ViewHolder(itemBinding.root) {
        val date = itemBinding.tvDate
        val completedSets = itemBinding.tvSets
        val completedTime = itemBinding.tvTime
        val delete = itemBinding.ivDelete

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val context = holder.itemView.context
            val item = items[position]
            holder.date.text = item.date
            holder.completedSets.text = item.completed_sets
            holder.completedTime.text = item.completed_time

            if (item.isCompleted){
                holder.itemView.setBackgroundColor(context.resources.getColor(R.color.light_green))
            }else {
                holder.itemView.setBackgroundColor(context.resources.getColor(R.color.white))
            }
            holder.delete.setOnClickListener {
                deleteListener(item.id)
            }
    }
}