package com.example.valutes

object ApiObject {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}