package com.example.valutes

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ValuteDatabaseDao {
    @Insert
    suspend fun insert(valute: ValuteEntity)

    @Query("DELETE FROM valutes WHERE id = :charCode")
    suspend fun delete(charCode: String)

    @Query("SELECT * FROM valutes WHERE id = :charCode")
    suspend fun getByCharCode(charCode: String): ValuteEntity

    @Query("DELETE FROM valutes")
    suspend fun clear()

    @Query("SELECT * FROM valutes")
    fun getAllValutes(): LiveData<List<ValuteEntity>>

    @Query("SELECT COUNT() FROM valutes")
    fun size(): Int

    @Query("SELECT CASE WHEN EXISTS (SELECT * FROM valutes WHERE id = :charCode) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END")
    suspend fun containsValute(charCode: String): Boolean

}