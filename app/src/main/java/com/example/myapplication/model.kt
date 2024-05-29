package com.example.myapplication
import com.google.gson.Gson
import java.io.File

open class WordWithEmbed(_word: String, _embed: Array<Double>) {
    var word: String = _word
    var embed: Array<Double> = _embed
}

class Attempt(_word: String, _embed: Array<Double>): WordWithEmbed(_word, _embed) {
    var score = 0.0
}

class Database(path: String) {
    private var wordData = mutableMapOf<String, Array<Double>>()

    init {
        class DataJson(val words: Array<WordWithEmbed>); // nested class for reading from json
        val file = File(path)
        if (file.length() == 0L) {                       // 0L - long
            throw Exception("No .json file")
        }
        val jsonString = file.readText()
        val dataFromJson = Gson().fromJson(jsonString, DataJson::class.java)

        for (word: WordWithEmbed in dataFromJson.words) {
            wordData[word.word] = word.embed
        }
    }

    fun wordToEmbedding(word: String): Array<Double>? {
        return wordData[word]
    }

    fun newWord(): WordWithEmbed {
        val idx = (0..wordData.size).random()
        val key = wordData.keys.elementAt(idx)

        return WordWithEmbed(key, wordData[key]!!)
    }
}


class VectorSpace {
    fun distance(first: WordWithEmbed, second: WordWithEmbed): Double {
        return first.embed[0]
    }

    fun norm(vec: Array<Double>): Double {
        return vec[0]
    }
}