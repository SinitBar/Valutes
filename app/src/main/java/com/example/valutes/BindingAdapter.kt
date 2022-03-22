package com.example.valutes

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("listData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    data: List<ValuteEntity>?
) {
    val adapter = recyclerView.adapter as CardRecyclerViewAdapter
    adapter.submitList(data)
}