package io.github.nullifythis.android.flashcards

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val KEY_REMAINING_WORDS = "KEY_REMAINING_WORDS"
        private const val KEY_INCORRECT_WORDS = "KEY_INCORRECT_WORDS"
        private const val KEY_IS_SHOWING_RETRIES = "KEY_IS_SHOWING_RETRIES"
    }

    private var remainingWords: MutableList<String> = mutableListOf()
    private var incorrectWords: MutableList<String> = mutableListOf()

    private var isShowingRetries = false

    private val hasWordsRemaining: Boolean
        get() = remainingWords.isNotEmpty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        container_content.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE

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
    }

    private fun initializeWordList() {
        remainingWords = Words.createRandomizedList().toMutableList()
        incorrectWords = mutableListOf()
        isShowingRetries = false
    }

    private fun restoreWordList(savedState: Bundle) {
        remainingWords = requireNotNull(
            savedState.getStringArray(KEY_REMAINING_WORDS)
        ).toMutableList()
        incorrectWords = requireNotNull(
            savedState.getStringArray(KEY_INCORRECT_WORDS)
        ).toMutableList()
        isShowingRetries = savedState.getBoolean(KEY_IS_SHOWING_RETRIES)
    }

    private fun requestResetWordList() {
        if (!hasWordsRemaining) {
            initializeWordList()
            updateDisplay()
        }
    }

    private fun onWordAnsweredCorrectly() {
        if (remainingWords.isEmpty()) { return }
        remainingWords.removeFirst()
        continueWithRetriesIfRequired()
        updateDisplay()
    }

    private fun onWordAnsweredIncorrectly() {
        if (remainingWords.isEmpty()) { return }
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
    }
}
