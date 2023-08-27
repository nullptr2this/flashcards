package io.github.nullifythis.android.flashcards

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.InputDeviceCompat
import io.github.nullifythis.android.flashcards.databinding.ActivityFlashCardsBinding
import nl.dionsegijn.konfetti.models.Shape.Circle
import nl.dionsegijn.konfetti.models.Shape.Square
import nl.dionsegijn.konfetti.models.Size

class FlashCardsActivity : AppCompatActivity() {

    companion object {

        fun startWith(context: Context, option: FlashCards.Options) {
            context.startActivity(intentFor(context, option))
        }

        private fun intentFor(context: Context?, option: FlashCards.Options): Intent {
            return Intent(context, FlashCardsActivity::class.java).apply {
                putExtra(EXTRA_WORDS_OPTION, option)
            }
        }

        private const val EXTRA_WORDS_OPTION = "EXTRA_WORDS_OPTION"
        private const val KEY_CORRECT_ANSWER_COUNT = "KEY_CORRECT_ANSWERS"
        private const val KEY_INCORRECT_ANSWER_COUNT = "KEY_INCORRECT_ANSWERS"
        private const val KEY_INCORRECT_WORDS = "KEY_INCORRECT_WORDS"
        private const val KEY_IS_SHOWING_RETRIES = "KEY_IS_SHOWING_RETRIES"
        private const val KEY_REMAINING_WORDS = "KEY_REMAINING_WORDS"
    }

    private val binding: ActivityFlashCardsBinding by lazy {
        ActivityFlashCardsBinding.inflate(layoutInflater)
    }

    private var correctAnswerCount = 0
    private var incorrectAnswerCount = 0
    private var isShowingRetries = false
    private var remainingWords: MutableList<String?> = mutableListOf()
    private var incorrectWords: MutableList<String?> = mutableListOf()

    private val hasWordsRemaining: Boolean
        get() = remainingWords.isNotEmpty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupForFullScreen()
        if (savedInstanceState != null) {
            restoreWordList(savedInstanceState)
        } else {
            initializeWordList()
        }
        binding.buttonAnswerCorrect.setOnClickListener { onWordAnsweredCorrectly() }
        binding.buttonAnswerIncorrect.setOnClickListener { onWordAnsweredIncorrectly() }
        binding.textSightWord.setOnLongClickListener {
            requestResetWordList()
            true
        }
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
        val flashCardOptions = intent.getSerializableExtra(EXTRA_WORDS_OPTION)?.let { it as? FlashCards.Options }
            ?: throw NullPointerException("null cannot be cast to non-null type io.github.nullifythis.android.flashcards.FlashCards.Options")
        remainingWords = FlashCards.createFlashCardsForOptions(flashCardOptions).toMutableList()
        incorrectWords = mutableListOf()
        isShowingRetries = false
        correctAnswerCount = 0
        incorrectAnswerCount = 0
    }

    private fun restoreWordList(savedState: Bundle) {
        remainingWords = savedState.getStringArray(KEY_REMAINING_WORDS)?.toMutableList()
            ?: throw IllegalArgumentException("Required value was null.")
        incorrectWords = savedState.getStringArray(KEY_INCORRECT_WORDS)?.toMutableList()
            ?: throw IllegalArgumentException("Required value was null.")
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
        if (remainingWords.isEmpty()) {
            return
        }
        correctAnswerCount++
        remainingWords.removeFirst()
        continueWithRetriesIfRequired()
        updateDisplay()
    }

    private fun onWordAnsweredIncorrectly() {
        if (remainingWords.isEmpty()) {
            return
        }
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
        val nextWordOrMessage = if (remainingWords.isNotEmpty())
            remainingWords.first() else "You did it!!!"
        binding.textSightWord.text = nextWordOrMessage
        binding.textWordCounter.text = String.format("%03d", remainingWords.size + incorrectWords.size)
        val textWordCounterColorRes = if (hasWordsRemaining && isShowingRetries) {
            R.color.text_color_word_counter_retries
        } else {
            R.color.text_color_word_counter
        }
        binding.textWordCounter.setTextColor(
            ColorStateList.valueOf(
                ContextCompat.getColor(this, textWordCounterColorRes)
            )
        )
        binding.textWordCountCorrect.text = String.format("%03d", correctAnswerCount)
        binding.textWordCountIncorrect.text = String.format("%03d", incorrectAnswerCount)
        if (!hasWordsRemaining) {
            val displayWidth = binding.containerConfetti.width
            binding.containerConfetti.build()
                .addColors(InputDeviceCompat.SOURCE_ANY, -16711936, -65281)
                .setDirection(0.0, 359.0).setSpeed(1.0f, 5.0f).setFadeOutEnabled(true)
                .setTimeToLive(2000L).addShapes(Square, Circle)
                .addSizes(Size(12, 2f))
                .setPosition(-50.0f, displayWidth + 50.0f, -50.0f, -50.0f)
                .streamFor(300, 5000L)
        }
    }
}
