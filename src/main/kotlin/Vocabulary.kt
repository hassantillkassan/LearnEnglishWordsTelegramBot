package org.example

fun main() {
    val trainer = LearnWordsTrainer()

    trainer.vocabulary.forEach {
        println("${it.text} - ${it.translate} (Количество правильных ответов - ${it.correctAnswersCount})")
    }
}

