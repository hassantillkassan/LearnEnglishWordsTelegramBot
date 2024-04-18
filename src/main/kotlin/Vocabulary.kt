package org.example

import java.io.File

fun main() {

    val vocabulary = loadVocabulary()

    vocabulary.forEach {
        println("${it.text} - ${it.translate} (Количество правильных ответов - ${it.correctAnswersCount})")
    }
}

fun loadVocabulary(): MutableList<Word> {

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

    return vocabulary
}

fun updateCorrectAnswersCount(vocabulary: MutableList<Word>, wordToUpdate: String) {
    val word = vocabulary.find { it.text == wordToUpdate }
    if (word != null) word.correctAnswersCount++
}

fun updateVocabulary(vocabulary: MutableList<Word>) {
    val content = vocabulary.joinToString("\n") { "${it.text}|${it.translate}|${it.correctAnswersCount}" }
    File("words.txt").writeText(content)
}