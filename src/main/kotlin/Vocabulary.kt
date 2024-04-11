package org.example

import java.io.File

data class Word(
    val text: String,
    val translate: String,
    val correctAnswersCount: Int = 0,
)

fun main() {

    val wordsFile = File("words.txt")

    val vocabulary: MutableList<Word> = mutableListOf()

    wordsFile.forEachLine {line: String ->  
        val parts = line.split("|")

        if (parts.size == 3) {
            val word = Word(
                text = parts[0],
                translate = parts[1],
                correctAnswersCount = parts.getOrNull(2)?.toInt() ?: 0,
            )

            vocabulary.add(word)
        }
    }

    wordsFile.appendText("\nКоличество правильных ответов: ${vocabulary.last().correctAnswersCount}")

    vocabulary.forEach{
        println("${it.text} - ${it.translate}. Количество правильных ответов - ${it.correctAnswersCount}")
    }
}