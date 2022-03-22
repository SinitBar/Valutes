package com.example.valutes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "valutes")
data class ValuteEntity(
    @ColumnInfo(name = "ID")
    val ID: String = "",

    @ColumnInfo(name = "NumCode")
    val NumCode: String = "",

    @PrimaryKey
    @ColumnInfo(name = "CharCode")
    val CharCode: String = "",

    @ColumnInfo(name = "Nominal")
    val Nominal: Int = 0,

    @ColumnInfo(name = "Name")
    val Name: String = "",

    @ColumnInfo(name = "Value")
    val Value: Double = 0.0,

    @ColumnInfo(name = "Previous")
    val Previous: Double = 0.0
) {
    fun getStringValue()= Value.toString()
    fun getStringPreviousValue() = Previous.toString()
}