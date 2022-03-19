package com.example.valutes

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.valutes.databinding.OneCardBinding

class CardRecyclerViewAdapter(
    private val listener: MainActivity
) : ListAdapter<ValuteEntity,
        CardViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<ValuteEntity>() {
        override fun areItemsTheSame(
            oldItem: ValuteEntity, newItem: ValuteEntity
        ): Boolean = oldItem === newItem

        override fun areContentsTheSame(
            oldItem: ValuteEntity, newItem: ValuteEntity
        ): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        Log.e("onCreateViewHolder", "called")
        return CardViewHolder(
            OneCardBinding.inflate(
                LayoutInflater.from(parent.context)
            ), listener
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val chosenValute = getItem(position)
        holder.bind(chosenValute)
        Log.e("onBindViewHolder", "bind $chosenValute to position $position")
    }
}