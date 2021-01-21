package io.github.nullifythis.android.flashcards

import kotlin.random.Random

object Words {

    val allWords: Set<String> = setOf(
        "from",
        "soon",
        "say",
        "four",
        "too"
    )

    fun createRandomizedList(): List<String> {
        val randomizedList = mutableListOf<String>()
        val allWordsList = allWords.toMutableList()
        while (allWordsList.isNotEmpty()) {
            val nextIndex = Random.nextInt(0, allWordsList.size)
            val nextWord = allWordsList.removeAt(nextIndex)
            randomizedList.add(nextWord)
        }
        return randomizedList
    }
}
