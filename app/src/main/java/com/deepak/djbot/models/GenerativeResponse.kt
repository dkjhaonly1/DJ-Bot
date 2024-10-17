package com.deepak.djbot

// Define the data class for the API response
data class GenerativeResponse(
    val choices: List<Choice>
)

// Define the data class for each choice in the response
data class Choice(
    val text: String
)
