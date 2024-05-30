package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.system.exitProcess
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonString: String = application.assets.open("embeddings.json").bufferedReader().use {
            it.readText()
        }
        val db: Database = Database(jsonString)
        val space: VectorSpace = VectorSpace()
        val answer = db.newWord()
        Log.d("TAG", answer.word)
        var attempts = mutableSetOf<WordWithEmbed>()

        val showAttempts = findViewById<LinearLayout>(R.id.AttemptList)
        val inputWord = findViewById<EditText>(R.id.input)
        val sendWord = findViewById<Button>(R.id.send)
        val last = findViewById<TextView>(R.id.lastAttempt)

        sendWord.setOnClickListener {
            var text = inputWord.text.toString().trim()
            inputWord.setText("")
            if (text == answer.word) {
                val intent: Intent = Intent(this, winActivity::class.java)
                intent.putExtra("count", attempts.size)
                startActivity(intent)
            }
            val embed = db.wordToEmbedding(text)
            if (embed == null) {
                last.setText("Я не знаю такого слова")
            }
            else {
                val newAttempt: WordWithEmbed = WordWithEmbed(text, embed)          // embed - smart cast
                //newAttempt.score = space.distance(answer, newAttempt)
                val score = space.distance(answer, newAttempt)
                last.setText(text)
                if (!attempts.contains(newAttempt)) {
                    attempts.add(newAttempt)
                    text += ": " + "%.2f".format(score)
                    val showAttempt = TextView(this)
                    showAttempt.setTextSize(20f)
                    showAttempt.setText(text)
                    showAttempts.addView(showAttempt)
                }
            }
        }

    }
}