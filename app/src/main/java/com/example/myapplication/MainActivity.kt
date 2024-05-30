package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonString: String = application.assets.open("embeddings.json").bufferedReader().use {
            it.readText()
        }
        val db: Database = Database(jsonString)

        val attempts = findViewById<LinearLayout>(R.id.AttemptList)
        val inputWord = findViewById<EditText>(R.id.input)
        val sendWord = findViewById<Button>(R.id.send)
        val last = findViewById<TextView>(R.id.lastAttempt)

        sendWord.setOnClickListener {
            var text = inputWord.text.toString().trim()
            val embed = db.wordToEmbedding(text)
            if (embed == null) {
                last.setText("Я не знаю такого слова")
            }
            else {
                text += ": 10"
                last.setText(text)
                val newAttempt = EditText(this)
                newAttempt.setText(text)
                attempts.addView(newAttempt)
            }
        }

    }
}