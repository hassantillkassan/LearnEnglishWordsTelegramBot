package org.example

const val SUCCESSFUL_NUM_OF_TIMES = 3
const val NUM_OF_CHOICES = 4

fun main() {

    val vocabulary = loadVocabulary()

    var userNavigation: Int?
    var enteredDigit: Int?

    do {
        println("Меню: 1 – Учить слова, 2 – Статистика, 0 – Выход")
        userNavigation = readln().toIntOrNull()

        when (userNavigation) {
            1 -> {
                do {
                    val listOfUnlearnedWords: List<Word> =
                        vocabulary.filter { it.correctAnswersCount < SUCCESSFUL_NUM_OF_TIMES }

                    if (listOfUnlearnedWords.isEmpty()) {
                        println("Вы выучили все слова")
                        break
                    }

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

                    println(correctAnswer.text)
                    println(answers.mapIndexed { index, it ->
                        "${index + 1})${it.translate}"
                    }.joinToString(separator = " ", postfix = ""))

                    println("Введите номер правильного ответа (Выход в меню - 0)")
                    enteredDigit = readln().toIntOrNull()

                    when (enteredDigit) {
                        (answers.indexOfFirst { it.text == correctAnswer.text } + 1) -> {
                            println("Верно")
                            correctAnswer.correctAnswersCount++

                            updateVocabulary(vocabulary)
                        }

                        0 -> break
                        else -> println("Неверно")
                    }

                } while (true)

            }

            2 -> {
                val totalWords = vocabulary.size
                val learnedWords = vocabulary.filter { it.correctAnswersCount >= SUCCESSFUL_NUM_OF_TIMES }.size

                println(
                    "Выучено $learnedWords " +
                            "из $totalWords | " +
                            "${(learnedWords * 100) / totalWords}%"
                )
            }

            0 -> break
            else -> println("Неверный ввод")
        }
    } while (true)

}

