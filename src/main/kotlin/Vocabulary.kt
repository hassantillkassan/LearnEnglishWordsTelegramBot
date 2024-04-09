package org.example

import java.io.File

fun main() {

    val wordsFile = File("src/main/kotlin/words.txt")

    wordsFile.readLines().forEach{ println(it) }

}