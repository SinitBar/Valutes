package com.example.valutes

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("daily_json.js")
    suspend fun getAll(): Response<RequestResult>
}