package io.github.nullifythis.android.flashcards

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

class MainActivity : AppCompatActivity() {

    companion object {
        fun intentFor(context: Context, option: Words.Options) =
            Intent(context, MainActivity::class.java).apply {
                putExtra(EXTRA_WORDS_OPTION, option)
            }
        private const val EXTRA_WORDS_OPTION = "EXTRA_WORDS_OPTION"

        private const val KEY_REMAINING_WORDS = "KEY_REMAINING_WORDS"
        private const val KEY_INCORRECT_WORDS = "KEY_INCORRECT_WORDS"
        private const val KEY_IS_SHOWING_RETRIES = "KEY_IS_SHOWING_RETRIES"
        private const val KEY_CORRECT_ANSWER_COUNT = "KEY_CORRECT_ANSWERS"
        private const val KEY_INCORRECT_ANSWER_COUNT = "KEY_INCORRECT_ANSWERS"
    }

    private var remainingWords: MutableList<String> = mutableListOf()
    private var incorrectWords: MutableList<String> = mutableListOf()

    private var isShowingRetries = false
    private var correctAnswerCount = 0
    private var incorrectAnswerCount = 0

    private val hasWordsRemaining: Boolean
        get() = remainingWords.isNotEmpty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupForFullScreen()

        savedInstanceState?.let { restoreWordList(it) }
            ?: initializeWordList()

        button_answer_correct.setOnClickListener { onWordAnsweredCorrectly() }
        button_answer_incorrect.setOnClickListener { onWordAnsweredIncorrectly() }
        text_sight_word.setOnLongClickListener { requestResetWordList(); true }

        updateDisplay()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArray(KEY_REMAINING_WORDS, remainingWords.toTypedArray())
        outState.putStringArray(KEY_INCORRECT_WORDS, incorrectWords.toTypedArray())
        outState.putBoolean(KEY_IS_SHOWING_RETRIES, isShowingRetries)
        outState.putInt(KEY_CORRECT_ANSWER_COUNT, correctAnswerCount)
        outState.putInt(KEY_INCORRECT_ANSWER_COUNT, incorrectAnswerCount)
    }

    private fun initializeWordList() {
        remainingWords = Words.createWordsForFlashCards(
            intent.getSerializableExtra(EXTRA_WORDS_OPTION) as Words.Options
        ).toMutableList()
        incorrectWords = mutableListOf()
        isShowingRetries = false
        correctAnswerCount = 0
        incorrectAnswerCount = 0
    }

    private fun restoreWordList(savedState: Bundle) {
        remainingWords = requireNotNull(
            savedState.getStringArray(KEY_REMAINING_WORDS)
        ).toMutableList()
        incorrectWords = requireNotNull(
            savedState.getStringArray(KEY_INCORRECT_WORDS)
        ).toMutableList()
        isShowingRetries = savedState.getBoolean(KEY_IS_SHOWING_RETRIES)
        correctAnswerCount = savedState.getInt(KEY_CORRECT_ANSWER_COUNT, 0)
        incorrectAnswerCount = savedState.getInt(KEY_INCORRECT_ANSWER_COUNT, 0)
    }

    private fun requestResetWordList() {
        if (!hasWordsRemaining) {
            initializeWordList()
            updateDisplay()
        }
    }

    private fun onWordAnsweredCorrectly() {
        if (remainingWords.isEmpty()) { return }
        correctAnswerCount++
        remainingWords.removeFirst()
        continueWithRetriesIfRequired()
        updateDisplay()
    }

    private fun onWordAnsweredIncorrectly() {
        if (remainingWords.isEmpty()) { return }
        incorrectAnswerCount++
        incorrectWords.add(remainingWords.removeFirst())
        continueWithRetriesIfRequired()
        updateDisplay()
    }

    private fun continueWithRetriesIfRequired() {
        if (remainingWords.isEmpty() && incorrectWords.isNotEmpty()) {
            remainingWords.addAll(incorrectWords)
            incorrectWords.clear()
            isShowingRetries = true
        }
    }

    private fun updateDisplay() {
        text_sight_word.text = if (remainingWords.isNotEmpty())
            remainingWords.first() else "You did it!!!"
        text_word_counter.text = "${remainingWords.size + incorrectWords.size}"
        text_word_counter.setTextColor(
            ColorStateList.valueOf(
                ContextCompat.getColor(this,
                    if (hasWordsRemaining && isShowingRetries)
                        R.color.text_color_word_counter_retries else
                        R.color.text_color_word_counter
                )
            )
        )
        text_word_count_correct.text = String.format("%03d", correctAnswerCount)
        text_word_count_incorrect.text = String.format("%03d", incorrectAnswerCount)

        if (!hasWordsRemaining) {
            container_confetti.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.Square, Shape.Circle)
                .addSizes(Size(12))
                .setPosition(-50f, container_confetti.width + 50f, -50f, -50f)
                .streamFor(300, 5000L)
        }
    }
}
