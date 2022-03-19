package com.example.valutes

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.valutes.databinding.OneCardBinding

class CardViewHolder(
    private var binding: OneCardBinding,
    private val listener: MainActivity
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    init {
        itemView.setOnClickListener(this)
        //binding.buttonFavorites.setOnClickListener(this)
       // binding.buttonShare.setOnClickListener(this)
    }

    interface OnItemClickListener {
        fun onItemClicked(positionInRecyclerView: Int)
        //
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    fun bind(valute: ValuteEntity) {
        Log.e("CardViewHolder", "function bind called")
        binding.vViewModel?.currentValute?.value = valute
       // binding.executePendingBindings() // causes the update to execute immediately
    }

}