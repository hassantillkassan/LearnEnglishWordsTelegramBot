package org.example

fun Question.asConsoleString(): String {
    val variants = this.variants.mapIndexed { index, it ->
        "${index + 1})${it.translate}"
    }.joinToString(separator = " ", postfix = "")

    return this.correctAnswer.text + "\n" + variants + "\n0)Меню"
}

fun main() {

    val trainer = LearnWordsTrainer()

    var userNavigation: Int?

    do {
        println("Меню: 1 – Учить слова, 2 – Статистика, 0 – Выход")
        userNavigation = readln().toIntOrNull()

        when (userNavigation) {
            1 -> {
                do {
                    val question = trainer.getNextQuestion()

                    if (question == null) {
                        println("Вы выучили все слова")
                        break
                    }

                    println(question.asConsoleString())

                    println("Введите номер правильного ответа (Выход в меню - 0)")
                    val userAnswerInput = readln().toIntOrNull()
                    if (userAnswerInput == 0) break

                    if (trainer.checkAnswer(userAnswerInput?.minus(1)))
                        println("Верно")
                    else
                        println("Неверно")

                } while (true)

            }

            2 -> {
                val statistics = trainer.getStatistics()
                println(
                    "Выучено ${statistics.learnedWords} " +
                            "из ${statistics.totalWords} | " +
                            "${statistics.percent}%"
                )
            }

            0 -> break
            else -> println("Неверный ввод")
        }
    } while (true)

}

