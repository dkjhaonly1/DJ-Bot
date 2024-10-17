package com.deepak.djbot

import ChatAdapter
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import android.util.Log
import kotlinx.coroutines.withContext

class ChatActivity : AppCompatActivity() {

    lateinit var searchField: EditText
    lateinit var sendButton: ImageView
    private lateinit var chatRecyclerView: RecyclerView
    private val chatMessages = mutableListOf<Pair<String, Boolean>>() // Pair of message and isUser flag
    private lateinit var chatAdapter: ChatAdapter

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = "AIzaSyDd7gFHxuGr-vJXEOBB4MRDQN3JsyuG7SE"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        searchField = findViewById(R.id.write_here)
        sendButton = findViewById(R.id.sendbtn)
        chatRecyclerView = findViewById(R.id.resulttv)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatAdapter = ChatAdapter()
        chatRecyclerView.adapter = chatAdapter

        sendButton.setOnClickListener {
            val userInput = searchField.text.toString().trim()
            if (userInput.isNotEmpty()) {
                chatMessages.add(Pair(userInput, true))
                chatAdapter.notifyItemInserted(chatMessages.size - 1)
                searchField.text.clear()
                sendMessageToBot(userInput)
            }
        }
    }



    private fun sendMessageToBot(message: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d("ChatActivity", "Initializing GenerativeModel...")

                // Initialize the model
                val generativeModel = GenerativeModel(
                    modelName = "gemini-1.5-flash",
                    apiKey = BuildConfig.apiKey
                )

                Log.d("ChatActivity", "GenerativeModel initialized successfully.")
                Log.d("ChatActivity", "Sending message to bot: $message")

                // Call the API
                val response = generativeModel.generateContent(message)

                if (response == null) {
                    throw Exception("Received null response from API")
                }

                Log.d("ChatActivity", "Received response: ${response.text}")

                val botReply = response.text ?: throw Exception("Bot returned null or empty text")

                withContext(Dispatchers.Main) {
                    chatMessages.add(Pair(botReply, false))
                    chatAdapter.notifyItemInserted(chatMessages.size - 1)
                    chatRecyclerView.scrollToPosition(chatMessages.size - 1)
                }
            } catch (e: HttpException) {
                Log.e("ChatActivity", "HTTP Error: ${e.message()}")
                handleError("HTTP Error: ${e.message()}")
            } catch (e: Exception) {
                Log.e("ChatActivity", "Exception: ${e.message}")
                handleError("Something unexpected happened: ${e.message}")
            }
        }
    }


    private fun handleError(errorMessage: String) {
        runOnUiThread {
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }
            Log.e("ChatActivity", errorMessage)
        }
    }






}
