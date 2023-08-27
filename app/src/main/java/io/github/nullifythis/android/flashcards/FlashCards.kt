package io.github.nullifythis.android.flashcards

import kotlin.random.Random.Default.nextInt

object FlashCards {

    enum class Options {
        LETTERS_ALL_IN_ORDER_LOWER_CASE,
        SIGHT_WORD_GROUP_FRYS_1ST,
        SIGHT_WORD_GROUP_FRYS_2ND,
        SIGHT_WORD_GROUP_FRYS_3RD,
        SIGHT_WORD_GROUP_FRYS_4TH,
        ADDITION_RESULTS_UNDER_10,
        ADDITION_RESULTS_EQUAL_TO_10,
        ADDITION_RESULTS_BETWEEN_10_AND_20
    }

    fun createFlashCardsForOptions(option: Options): List<String> {

        val createAlphabetLowerCase: List<String> = when (option) {
                Options.LETTERS_ALL_IN_ORDER_LOWER_CASE -> createAlphabetLowerCase()
                Options.SIGHT_WORD_GROUP_FRYS_1ST -> createSightWordListWithGroup(Words.frys1st100)
                Options.SIGHT_WORD_GROUP_FRYS_2ND -> createSightWordListWithGroup(Words.frys2nd100)
                Options.SIGHT_WORD_GROUP_FRYS_3RD -> createSightWordListWithGroup(Words.frys3rd100)
                Options.SIGHT_WORD_GROUP_FRYS_4TH -> createSightWordListWithGroup(Words.frys4th100)
                Options.ADDITION_RESULTS_UNDER_10 -> randomizeListOfFlashCards(Math.createFlashCardsForAdditionBelow10())
                Options.ADDITION_RESULTS_EQUAL_TO_10 -> randomizeListOfFlashCards(Math.createFlashCardsForAdditionTo10())
                Options.ADDITION_RESULTS_BETWEEN_10_AND_20 -> randomizeListOfFlashCards(Math.createFlashCardsForAdditionResultingBetween10And20())
            }

        return createAlphabetLowerCase.take(25)
    }

    private fun createAlphabetLowerCase(): List<String> {
        return ('a'..'z').map { it.toString() }
    }

    private fun createAlphabet(): List<String> {
        return ('A'..'Z').map { it.toString() }
    }

    private fun createRandomizedAlphabet(): List<String> {
        return randomizeListOfFlashCards(createAlphabet())
    }

    private fun createRandomizedNumbers(upperBound: Int): List<String> {
        return randomizeListOfFlashCards((0..upperBound).map { it.toString() })
    }

    private fun createSightWordListWithGroup(set: Set<String>): List<String> {
        return randomizeListOfFlashCards(set.toList())
    }

    private fun createSightWordListWithNewWords(): List<String> {
        return randomizeListOfFlashCards(Words.newWords.toList())
    }

    private fun createSightWordListWithAllWords(): List<String> {
        return randomizeListOfFlashCards(Words.allWords.toList()).take(35)
    }

    private fun randomizeListOfFlashCards(list: List<String>): List<String> {
        val currentList: MutableList<String> = list.toMutableList()
        val randomizedList: MutableList<String> = mutableListOf()
        while (currentList.isNotEmpty()) {
            val nextIndex = nextInt(0, currentList.size)
            val nextWord = currentList.removeAt(nextIndex)
            randomizedList.add(nextWord)
        }
        return randomizedList
    }
}
