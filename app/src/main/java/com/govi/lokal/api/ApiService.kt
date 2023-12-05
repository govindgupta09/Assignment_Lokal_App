package com.govi.lokal.api

import com.govi.lokal.model.MyData
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/products")
    fun fetchData(): Call<MyData>
}