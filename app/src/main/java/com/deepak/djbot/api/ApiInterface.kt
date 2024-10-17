// ApiInterface.kt
package com.deepak.djbot.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("your-endpoint")  // Replace with actual endpoint
    suspend fun getBotResponse(@Body requestBody: RequestBody): Response<GeminiResponse>
}

data class RequestBody(val prompt: String)
data class GeminiResponse(val text: String)
