package org.example

const val SUCCESSFUL_NUM_OF_TIMES = 3

fun main() {

    val vocabulary = loadVocabulary()

    var userNavigation: Int?

    do {
        println("Меню: 1 – Учить слова, 2 – Статистика, 0 – Выход")
        userNavigation = readln().toIntOrNull()

        when (userNavigation) {
            1 -> println("Учить слова")
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

