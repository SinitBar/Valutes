package com.example.valutes

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("listData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    data: LiveData<List<ValuteEntity>>
) {
    val adapter = recyclerView.adapter as CardRecyclerViewAdapter
    adapter.submitList(data.value)
    Log.e("bindRecyclerView", "binding adapter called with data: ${data.value}")
}