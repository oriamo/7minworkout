package com.example.a7minworkout

import android.graphics.Color
import android.provider.CalendarContract.Colors
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minworkout.databinding.ItemExcersiseStatusBinding

class ExcersiseAdapter( val dataSorce : ArrayList<Excersises>):RecyclerView.Adapter<ExcersiseAdapter.viewHolder>() {
    inner class viewHolder(val itemBinding : ItemExcersiseStatusBinding): RecyclerView.ViewHolder(itemBinding.root) {
            fun bindItem(currentData : Excersises){
                var id = currentData.getId()
                itemBinding.textView.text = id.toString()
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(ItemExcersiseStatusBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun getItemCount(): Int {
        return dataSorce.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        var currentData = dataSorce[position]
        holder.bindItem(currentData)

        when{
            currentData.getIsSelected() ->{
                holder.itemBinding.textView.background = ContextCompat.getDrawable(holder.itemBinding.textView.context,
                    R.drawable.green_background
                )
                holder.itemBinding.textView.setTextColor(Color.parseColor("#FFFFFF"))
            }
            currentData.getIsCompleted() -> {
                holder.itemBinding.textView.background = ContextCompat.getDrawable(holder.itemBinding.textView.context,
                    R.drawable.circular_color_accent_background
                )
                holder.itemBinding.textView.setTextColor(Color.parseColor("#000000"))
            }
        }
    }

}