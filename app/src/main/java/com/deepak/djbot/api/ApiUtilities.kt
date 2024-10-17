package com.deepak.djbot.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtilities {
    fun getApiInterface(): ApiInterface {
        return Retrofit.Builder()
            .baseUrl("https://generativelanguage.googleapis.com")  // Replace with your API endpoint
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}
