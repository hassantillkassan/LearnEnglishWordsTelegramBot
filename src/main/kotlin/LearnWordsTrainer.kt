package org.example

import java.io.File

const val SUCCESSFUL_NUM_OF_TIMES = 3
const val NUM_OF_CHOICES = 4

data class Statistics(
    val totalWords: Int,
    val learnedWords: Int,
    val percent: Int,
)

data class Question(
    var variants: MutableList<Word>,
    val correctAnswer: Word,
)

class LearnWordsTrainer {

    private var question: Question? = null
    val vocabulary = loadVocabulary()

    fun getStatistics(): Statistics {

        val totalWords = vocabulary.size
        val learnedWords = vocabulary.filter { it.correctAnswersCount >= SUCCESSFUL_NUM_OF_TIMES }.size
        val percent = (learnedWords * 100) / totalWords

        return Statistics(totalWords, learnedWords, percent)
    }

    fun getNextQuestion(): Question? {

        val listOfUnlearnedWords: List<Word> = vocabulary
            .filter { it.correctAnswersCount < SUCCESSFUL_NUM_OF_TIMES }
        if (listOfUnlearnedWords.isEmpty()) return null

        val answers = listOfUnlearnedWords.shuffled().take(NUM_OF_CHOICES).toMutableList()
        val correctAnswer = answers.random()

        if (answers.size < NUM_OF_CHOICES) {
            val learnedWords = vocabulary
                .filter { it.correctAnswersCount >= SUCCESSFUL_NUM_OF_TIMES }
                .shuffled()
                .take(NUM_OF_CHOICES - answers.size)

            answers += learnedWords
            answers.shuffle()

        }

        question = Question(
            variants = answers,
            correctAnswer = correctAnswer,
        )
        return question
    }

    fun checkAnswer(userAnswerIndex: Int?): Boolean {

        return question?.let {
            val correctAnswerIndex = it.variants.indexOf(it.correctAnswer)

            if (correctAnswerIndex == userAnswerIndex) {
                it.correctAnswer.correctAnswersCount++
                updateVocabulary(vocabulary)
                true
            } else {
                false
            }
        } ?: false
    }

     private fun loadVocabulary(): MutableList<Word> {

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

     private fun updateVocabulary(vocabulary: MutableList<Word>) {

        val content = vocabulary
            .joinToString("\n") { "${it.text}|${it.translate}|${it.correctAnswersCount}" }

        File("words.txt").writeText(content)
    }
}


