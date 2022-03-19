package com.example.valutes

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ValutesViewModel(
    val database: ValuteDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var posted = false

//    val recyclerViewProperties: MutableLiveData<List<ValuteEntity>> by lazy {
//        MutableLiveData<List<ValuteEntity>>()
//    }

    val currentValute: MutableLiveData<ValuteEntity> by lazy {
        MutableLiveData<ValuteEntity>()
    }

    fun initModel(context: Context? = null) {

        Log.e("initModel", "in here")
        viewModelScope.launch {
            try {
                val reqResult = withContext(Dispatchers.IO) {
                    ApiObject.retrofitService.getAll()
                }
                if (reqResult.body() == null && context != null) {
                    Toast.makeText(
                        context,
                        "There are some problems with the site",
                        Toast.LENGTH_LONG
                    ).show()
                }
                reqResult.body()?.let { requestResult ->
                    val list = requestResult.Valute.getValutesList()
                    if (!posted) {
                        //recyclerViewProperties.value = list
                        currentValute.value = list[0]
                        posted = true
                    }
                    else {
                        database.clear()
                        //recyclerViewProperties.postValue(list)
                        currentValute.postValue(list[0])
                    }
                    //Log.e("MainActivity", "initialization Model/////")
                   // recyclerViewProperties.value?.let {
                        Log.e("MainActivity", "value of properties: $list")
                        for(v in list) {
                            insertValute(v)
                        }
                    //}
                }
            } catch (e: Exception) {
                if (context != null) {
                    Toast.makeText(
                        context,
                        "ERROR! There are some problems with the site",
                        Toast.LENGTH_LONG
                    ).show()
                }
                Log.e("MainActivity", "error: e = $e")
            }
        }
        Log.e("getList", "started call")
        getList() // to check
        Log.e("getList", "ended call")
    }

    fun getList(): LiveData<List<ValuteEntity>> {
        Log.e("getList", "properties: ${database.getAllValutes().value}")
        //return recyclerViewProperties.value ?: emptyList()
        return database.getAllValutes()
    }

    suspend fun insertValute(valute: ValuteEntity) {
        val job = viewModelScope.launch(Dispatchers.IO) {
            if (database.containsValute(valute.CharCode))
                database.delete(valute.CharCode)
        }
        job.join()
        val valut = ValuteEntity(
            ID = valute.ID,
            NumCode = valute.NumCode,
            CharCode = valute.CharCode,
            Nominal = valute.Nominal,
            Name = valute.Name,
            Value = valute.Value,
            Previous = valute.Previous
        )
        viewModelScope.launch(Dispatchers.IO) {
            database.insert(valut)
        }
    }

    fun getCurrentCurrentValue() = currentValute.value?.Value.toString()
    fun getCurrentPreviousValue() = currentValute.value?.Previous.toString()
}