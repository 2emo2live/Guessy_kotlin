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

        val db: Database = Database("")

        val attempts = findViewById<LinearLayout>(R.id.AttemptList)
        val inputWord = findViewById<EditText>(R.id.input)
        val sendWord = findViewById<Button>(R.id.send)

        sendWord.setOnClickListener {
            val text = inputWord.text.toString().trim()
            val newAttempt = EditText(this)
            newAttempt.setText(text)
            attempts.addView(newAttempt)
        }

    }
}