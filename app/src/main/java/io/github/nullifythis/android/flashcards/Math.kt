package io.github.nullifythis.android.flashcards

object Math {

    enum class AnswerWith {
        Result, FirstOperand, SecondOperand
    }

    fun createFlashCardsForAdditionTo10(): List<String> {
        return createFlashCardsForNumbersThatAddTo(10, AnswerWith.FirstOperand)
            .plus(createFlashCardsForNumbersThatAddTo(10, AnswerWith.SecondOperand))
    }

    fun createFlashCardsForAdditionBelow10(): List<String> {
        return createFlashCardsForNumbersThatAddTo(1..9)
    }

    fun createFlashCardsForAdditionResultingBetween10And20(): List<String> {
        return createFlashCardsForNumbersThatAddTo(10..20)
    }

    private fun createFlashCardsForNumbersThatAddTo(targetRange: IntRange): List<String> {
        return targetRange.fold(mutableListOf()) { acc, target ->
            acc.apply {
                addAll(createFlashCardsForNumbersThatAddTo(target, AnswerWith.Result))
                addAll(createFlashCardsForNumbersThatAddTo(target, AnswerWith.FirstOperand))
                addAll(createFlashCardsForNumbersThatAddTo(target, AnswerWith.SecondOperand))
            }
        }
    }

    private fun createFlashCardsForNumbersThatAddTo(target: Int, answerWith: AnswerWith): List<String> {
        return 1.until(target).map {
            when (answerWith) {
                AnswerWith.Result -> "$it + ${(target - it)} = _"
                AnswerWith.FirstOperand -> "_ + $it = $target"
                AnswerWith.SecondOperand -> "$it + _ = $target"
            }
        }
    }
}
