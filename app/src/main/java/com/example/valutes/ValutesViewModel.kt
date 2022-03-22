package com.example.valutes

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

class ValutesViewModel(
    val database: ValuteDatabaseDao,
    val appl: Application,
) : AndroidViewModel(appl) {

    var sizeDB = 0

    val recyclerViewProperties: MutableLiveData<List<ValuteEntity>> by lazy {
        MutableLiveData<List<ValuteEntity>>()
    }

    override fun onCleared() {
        super.onCleared()
        sharedPreferences.edit().remove(keySizeDB).apply()
        sharedPreferences.edit().putInt(keySizeDB, sizeDB).apply()
        viewModelScope.launch {
            database.clear()
            val list = recyclerViewProperties.value ?: emptyList()
            for (v in list)
                insertValute(v)
        }
    }

    private val _requestResult = MutableLiveData<Response<RequestResult>>()

    val requestResult: LiveData<Response<RequestResult>>
        get() = _requestResult

    var currentValuteNumber = MutableLiveData<Int>()

    init {
        if (sizeDB == 0)
            initModel()
        else
            initfromDB()
    }

    fun initfromDB() {
        viewModelScope.launch {
            recyclerViewProperties.value = database.getAllValutes().value
        }
    }

    fun initModel() {
        val context = appl.applicationContext
        viewModelScope.launch {
            try {
                database.clear()
                _requestResult.value = ApiObject.retrofitService.getAll()
                requestResult.value?.body()?.let { requestResult ->
                    recyclerViewProperties.value = requestResult.Valute.getValutesList()
                    for (v in requestResult.Valute.getValutesList())
                        insertValute(v)
                }
            } catch (e: Exception) {
                if (context != null) {
                    Toast.makeText(
                        context,
                        "ERROR! There are some problems with the site",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    }

    suspend fun insertValute(valute: ValuteEntity) {
        val job = viewModelScope.launch {
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
        viewModelScope.launch {
            database.insert(valut)
        }
    }
}