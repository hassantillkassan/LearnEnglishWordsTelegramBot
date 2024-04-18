package org.example

const val SUCCESSFUL_NUM_OF_TIMES = 3

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

                    if (listOfUnlearnedWords.isEmpty()) break

                    val answers = listOfUnlearnedWords.shuffled().take(4)
                    val question = answers.random().text

                    println(question)
                    println(answers.mapIndexed { index, it ->
                        "${index + 1})${it.translate}"
                    }.joinToString(separator = " ", postfix = ""))

                    println("Введите номер правильного ответа (Выход в меню - 0)")
                    enteredDigit = readln().toIntOrNull()

                    when (enteredDigit) {
                        (answers.indexOfFirst { it.text == question } + 1) -> {
                            println("Верно")
                            updateCorrectAnswersCount(vocabulary, question)
                        }

                        0 -> break
                        else -> println("Неверно")
                    }

                } while (true)

                updateVocabulary(vocabulary)
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

