package org.example

import java.io.File

fun main() {

    val wordsFile = File("words.txt")

    val vocabulary: MutableList<Word> = mutableListOf()

    wordsFile.forEachLine { line: String ->
        val parts = line.split("|")

        if (parts.size == 3) {
            val word = Word(
                text = parts[0],
                translate = parts[1],
                correctAnswersCount = parts.getOrNull(2)?.toIntOrNull() ?: 0,
            )
            vocabulary.add(word)
        }
    }

    vocabulary.forEach {
        println("${it.text} - ${it.translate} (Количество правильных ответов - ${it.correctAnswersCount})")
    }
}