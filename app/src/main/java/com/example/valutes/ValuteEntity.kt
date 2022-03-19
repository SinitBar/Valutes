package com.example.valutes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "valutes")
data class ValuteEntity(
    @ColumnInfo(name = "ID")
    val ID: String,

    @ColumnInfo(name = "NumCode")
    val NumCode: String,

    @PrimaryKey
    @ColumnInfo(name = "CharCode")
    val CharCode: String,

    @ColumnInfo(name = "Nominal")
    val Nominal: Int,

    @ColumnInfo(name = "Name")
    val Name: String,

    @ColumnInfo(name = "Value")
    val Value: Double,

    @ColumnInfo(name = "Previous")
    val Previous: Double
)
