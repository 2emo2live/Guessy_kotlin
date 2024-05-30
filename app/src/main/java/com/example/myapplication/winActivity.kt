package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlin.system.exitProcess

class winActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win)

        val header = findViewById<TextView>(R.id.countAttempts)
        val start = findViewById<Button>(R.id.startWinButton)
        val exit = findViewById<Button>(R.id.exitWinButton)

        val count = intent.getIntExtra("count", 0) + 1
        val countText = "Затрачено $count попыток"
        header.setText(countText)

        start.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        exit.setOnClickListener {
            System.exit(0)
        }
    }
}