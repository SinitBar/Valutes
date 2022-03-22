package com.example.valutes

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.valutes.databinding.OneCardBinding

class CardViewHolder(
    private var binding: OneCardBinding,
    private val listener: OnItemClickListener
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    interface OnItemClickListener {
        fun onItemClicked(valute: ValuteEntity)
    }

    override fun onClick(p0: View?) {
        val currentPosition = adapterPosition
        if (currentPosition != RecyclerView.NO_POSITION) {
            val curVal = binding.oneValute ?: ValuteEntity()
            listener.onItemClicked(curVal)
        }
    }

    fun bind(valute: ValuteEntity) {
        binding.oneValute = valute
        binding.executePendingBindings() // causes the update to execute immediately
    }
}