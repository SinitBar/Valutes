package com.example.valutes

import java.util.*

data class RequestResult
    (
    val Date: Date,
    val PreviousDate: Date,
    val PreviousURL: String,
    val Timestamp: Date,
    val Valute: ExistingValutes
)