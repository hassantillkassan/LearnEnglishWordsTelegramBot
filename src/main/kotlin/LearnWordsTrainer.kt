package org.example

import java.io.File


data class Statistics(
    val totalWords: Int,
    val learnedWords: Int,
    val percent: Int,
)

data class Question(
    var variants: MutableList<Word>,
    val correctAnswer: Word,
)

class LearnWordsTrainer (
    private val successfulNumOfTimes: Int = 3,
    private val numOfChoices: Int = 4,
    private val fileName: String = "words.txt",
) {

    private var question: Question? = null
    private val vocabulary = loadVocabulary()

    fun getStatistics(): Statistics {

        val totalWords = vocabulary.size
        val learnedWords = vocabulary.filter { it.correctAnswersCount >= successfulNumOfTimes }.size
        val percent = (learnedWords * 100) / totalWords

        return Statistics(totalWords, learnedWords, percent)
    }

    fun getNextQuestion(): Question? {

        val listOfUnlearnedWords: List<Word> = vocabulary
            .filter { it.correctAnswersCount < successfulNumOfTimes }
        if (listOfUnlearnedWords.isEmpty()) return null

        val answers = listOfUnlearnedWords.shuffled().take(numOfChoices).toMutableList()
        val correctAnswer = answers.random()

        if (answers.size < numOfChoices) {
            val learnedWords = vocabulary
                .filter { it.correctAnswersCount >= successfulNumOfTimes }
                .shuffled()
                .take(numOfChoices - answers.size)

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

     private fun loadVocabulary(): List<Word> {

        val wordsFile = File(fileName)
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

     private fun updateVocabulary(vocabulary: List<Word>) {

        val content = vocabulary
            .joinToString("\n") { "${it.text}|${it.translate}|${it.correctAnswersCount}" }

        File(fileName).writeText(content)
    }
}


