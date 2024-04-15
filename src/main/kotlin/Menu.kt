package org.example

fun main() {

    val vocabulary = loadVocabulary()
    val totalWords = vocabulary.size
    val learnedWords = vocabulary.filter { it.correctAnswersCount >= 3 }.size

    var userNavigation: Int?

    do {
        println("Меню: 1 – Учить слова, 2 – Статистика, 0 – Выход")
        userNavigation = readln().toIntOrNull()

        when (userNavigation) {
            1 -> println("Учить слова")
            2 -> {
                println(
                    "Выучено $learnedWords " +
                            "из $totalWords | " +
                            "${(learnedWords * 100) / totalWords}"
                )
            }

            0 -> break
            else -> println("Неверный ввод")
        }
    } while (true)

}

