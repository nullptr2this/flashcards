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

    fun createFlashCardsForTimesTablesTo10(): List<String> {
        return createFlashCardsForTimesTablesFor(0 .. 10, 10)
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

    private fun createFlashCardsForTimesTablesFor(timesTables: IntRange, dimension: Int): List<String> {
        return timesTables.fold(mutableListOf()) { acc, timesTable ->
            acc.apply {
                addAll(createFlashCardsForTimesTable(timesTable, dimension))
            }
        }
    }

    private fun createFlashCardsForTimesTable(timesTable: Int, dimension: Int): List<String> {
        return 0.rangeTo(dimension).fold(mutableListOf()) { acc, operand2 ->
            acc.apply {
                add(createFlashCardForMultiplicationOf(timesTable, operand2, AnswerWith.Result))
                add(createFlashCardForMultiplicationOf(timesTable, operand2, AnswerWith.FirstOperand))
                add(createFlashCardForMultiplicationOf(timesTable, operand2, AnswerWith.SecondOperand))
            }
        }
    }

    private fun createFlashCardForMultiplicationOf(operand1: Int, operand2: Int, answerWith: AnswerWith): String {
        return when (answerWith) {
            AnswerWith.Result -> "$operand1 x $operand2 = _"
            AnswerWith.FirstOperand -> "_ x $operand2 = ${operand1 * operand2}"
            AnswerWith.SecondOperand -> "$operand1 x _ = ${operand1 * operand2}"
        }
    }
}
