package com.echoeyecodes.testapplication.utils

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private lateinit var retrofit: Retrofit

    companion object {
        const val BASE_URL = "https://6294f25b63b5d108c1977427.mockapi.io/"
        private var instance: ApiClient? = null
        fun getInstance() = ApiClient()
    }

    init {
        init()
    }

    private fun init() {
        val gson = GsonBuilder().setLenient().create()
        retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    fun <T> getClient(t: Class<T>?): T {
        return retrofit.create(t)
    }
}