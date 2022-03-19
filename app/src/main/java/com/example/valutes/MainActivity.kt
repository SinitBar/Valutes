package com.example.valutes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.valutes.databinding.ActivityMainBinding
import com.example.valutes.databinding.OneCardBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dDAO = ValuteDatabase.getInstance(applicationContext).valuteDatabaseDao

        val vmFactory = ValuteViewModelFactory(dDAO, application)

        val vVModel = ViewModelProvider(this, vmFactory)[ValutesViewModel::class.java]

        // call init for vVModel!!!! but how?
        vVModel.initModel(applicationContext)

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val cardBinding =
            DataBindingUtil.setContentView<OneCardBinding>(this, R.layout.one_card)

        binding.lifecycleOwner = this
        cardBinding.lifecycleOwner = this

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)

        binding.recyclerView.adapter = CardRecyclerViewAdapter(this)

        cardBinding.vViewModel = vVModel
        binding.vViewModel = vVModel

        // Create the observer which updates the UI.
        val recyclerViewObserver = Observer<List<ValuteEntity>> {
            // Update the UI
            // but how?
            bindRecyclerView(binding.recyclerView, vVModel.getList())
        }

        vVModel.getList().observe(this, recyclerViewObserver)

        Log.i("MainActivity", "done")
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}