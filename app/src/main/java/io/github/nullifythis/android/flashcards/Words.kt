package io.github.nullifythis.android.flashcards

import kotlin.random.Random

object Words {

    enum class Options {
        WORDS_NEW,
        WORDS_ALL,
        LETTERS_ORDERED,
        LETTERS_RANDOM
    }

    fun createWordsForFlashCards(option: Options): List<String> {
        return when (option) {
            Options.WORDS_NEW -> createSightWordListWithNewWords()
            Options.WORDS_ALL -> createSightWordListWithAllWords()
            Options.LETTERS_ORDERED -> createAlphabet()
            Options.LETTERS_RANDOM -> createRandomizedAlphabet()
        }
    }

    private fun createSightWordListWithNewWords(): List<String> {
        return randomizeListOfWords(newWords.toList())
    }

    private fun createSightWordListWithAllWords(): List<String> {
        return randomizeListOfWords(allWords.toList()).take(25)
    }

    private fun createAlphabet(): List<String> {
        return ('A'..'Z').toList().map {
            it.toString() + it.plus(32)
        }
    }

    private fun createRandomizedAlphabet(): List<String> {
        return randomizeListOfWords(
            createAlphabet()
        )
    }

    fun createRandomizedNumbers(upperBound: Int): List<String> {
        return randomizeListOfWords(
            (0..upperBound).asIterable()
                .map { it.toString() }
        )
    }

    private fun randomizeListOfWords(listOfWords: List<String>): List<String> {
        val currentListOfWords = listOfWords.toMutableList()
        val randomizedList = mutableListOf<String>()
        while (currentListOfWords.isNotEmpty()) {
            val nextIndex = Random.nextInt(0, currentListOfWords.size)
            val nextWord = currentListOfWords.removeAt(nextIndex)
            randomizedList.add(nextWord)
        }
        return randomizedList
    }

    private val newWords: Set<String> = setOf(

        "or",
        "these",
        "drink",
        "once",

        "put",
        "full",
        "found",
        "sing",

        "round",
        "cut",
        "grow",
        "them",

        "sit",
        "use",
        "long",
        "were",

        "then",
        "both",
        "many",
        "work",
    )

    private val allWords: Set<String> = setOf(
        "a",
        "about",
        "again",
        "all",
        "am",
        "an",
        "and",
        "any",
        "are",
        "as",
        "ate",
        "away",
        "be",
        "because",
        "been",
        "before",
        "best",
        "big",
        "black",
        "blue",
        "brown",
        "but",
        "by",
        "call",
        "came",
        "cold",
        "come",
        "did",
        "do",
        "does",
        "done",
        "down",
        "eat",
        "eight",
        "every",
        "fall",
        "far",
        "find",
        "five",
        "fly",
        "from",
        "for",
        "four",
        "funny",
        "gave",
        "get",
        "give",
        "goes",
        "going",
        "good",
        "green",
        "grey",
        "had",
        "has",
        "have",
        "he",
        "help",
        "her",
        "here",
        "him",
        "his",
        "how",
        "I",
        "if",
        "in",
        "into",
        "its",
        "jump",
        "just",
        "kind",
        "laugh",
        "let",
        "like",
        "little",
        "look",
        "made",
        "me",
        "must",
        "new",
        "nine",
        "no",
        "not",
        "now",
        "of",
        "off",
        "on",
        "one",
        "orange",
        "our",
        "out",
        "pink",
        "play",
        "please",
        "pretty",
        "purple",
        "ran",
        "red",
        "ride",
        "run",
        "said",
        "saw",
        "say",
        "see",
        "seven",
        "shall",
        "she",
        "six",
        "so",
        "soon",
        "stop",
        "take",
        "ten",
        "that",
        "there",
        "they",
        "this",
        "three",
        "to",
        "too",
        "two",
        "up",
        "want",
        "was",
        "we",
        "well",
        "went",
        "what",
        "when",
        "where",
        "which",
        "white",
        "who",
        "why",
        "will",
        "with",
        "yes",
        "zero",

        "hold",
        "old",
        "us",
        "carry",
        "hot",
        "pull",
        "walk",
        "draw",
        "keep",
        "read",
        "your",
        "fast",
        "light",

        "after",
        "make",
        "ask",
        "wish",
        "first",
        "pick",
        "show",
        "around",
        "got",
        "right",
        "tell",
        "better",
        "hurt",
        "try",
        "upon",
        "could",
        "live",
        "always",
        "wash",
        "don't",
        "may",
    )
}
