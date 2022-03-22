package com.example.valutes

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.valutes.databinding.ActivityMainBinding
import kotlin.math.roundToInt

import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText

lateinit var sharedPreferences: SharedPreferences
const val keySizeDB = "DB_SIZE"

class MainActivity : AppCompatActivity(), CardViewHolder.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        val dDAO = ValuteDatabase.getInstance(applicationContext).valuteDatabaseDao

        val vmFactory = ValuteViewModelFactory(dDAO, application)

        val vVModel = ViewModelProvider(this, vmFactory)[ValutesViewModel::class.java]

        vVModel.sizeDB = sharedPreferences.getInt(keySizeDB, 0)

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.lifecycleOwner = this

        binding.recyclerView.setHasFixedSize(true)

        binding.recyclerView.adapter = CardRecyclerViewAdapter(this)

        binding.vViewModel = vVModel.also {
            it.currentValuteNumber.value = 0
        }

        binding.buttonRefresh.setOnClickListener { vVModel.initModel() }
    }

    fun setRusSumText() {
        val searchSum = findViewById<TextInputEditText>(R.id.search_sum)
        if (searchSum.text.toString()[0] == '.')
            searchSum.setText(searchSum.text?.drop(1))
        val sum = (
                searchSum.text.toString().toDouble() * 10000
                ).roundToInt() / 10000.0
        searchSum.setText(sum.toString())
        searchSum.setSelection(sum.toString().length)
    }

    override fun onItemClicked(valute: ValuteEntity) {
        setRusSumText()
        val textView = findViewById<TextView>(R.id.text_converted)
        val rusSumString = findViewById<TextInputEditText>(R.id.search_sum).text.toString()
        var rusSum = 0.0
        if (rusSumString != "" && rusSumString != ".")
        {
            rusSum = rusSumString.toDouble()
            val nominal = valute.Nominal
            val valuteCourse = valute.Value
            val result = (((nominal  * rusSum / valuteCourse)  * 10000).roundToInt() / 10000.0).toString()
            textView.text = resources.getString(
                R.string.string_converted,
                valute.CharCode, System.lineSeparator(), result
            )
        }

    }
}