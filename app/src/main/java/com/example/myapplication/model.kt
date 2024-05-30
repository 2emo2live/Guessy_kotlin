package com.example.myapplication
import com.google.gson.Gson
import java.security.KeyStore

open class WordWithEmbed(_word: String, _embed: Array<Double>) {
    var word: String = _word
    var embed: Array<Double> = _embed

    override fun equals(other: Any?): Boolean {
        if (other !is WordWithEmbed)
            return false
        val otherWord = other as WordWithEmbed
        return this.word == other.word
    }

    override fun hashCode(): Int {
        return this.word.hashCode()
    }
}

//class Attempt(_word: String, _embed: Array<Double>): WordWithEmbed(_word, _embed) {
//    var score = 0.0
//}

class Database(jsonString: String) {
    private var wordData = mutableMapOf<String, Array<Double>>()

    init {
        class DataJson(val words: Array<WordWithEmbed>); // nested class for reading from json
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
        val multNorm: Double = norm(first.embed) * norm(second.embed)
        var res: Double = 0.0
        for (i: Int in (0..first.embed.size-1))
            res += first.embed[i] * second.embed[i]
        return kotlin.math.abs(res / multNorm)
    }

    fun norm(vec: Array<Double>): Double {
        var res: Double = 0.0
        for (i: Double in vec)
            res += i*i
        return kotlin.math.sqrt(res)
    }
}