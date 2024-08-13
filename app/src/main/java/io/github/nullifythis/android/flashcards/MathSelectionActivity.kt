package io.github.nullifythis.android.flashcards

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.nullifythis.android.flashcards.FlashCardsActivity.Companion.startWith
import io.github.nullifythis.android.flashcards.databinding.ActivityMathSelectionBinding

class MathSelectionActivity : AppCompatActivity() {

    private val binding: ActivityMathSelectionBinding by lazy {
        ActivityMathSelectionBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupForFullScreen()
        binding.buttonAdditionUnder10.setOnClickListener {
            startFlashCardsFor(FlashCards.Options.ADDITION_RESULTS_UNDER_10)
        }
        binding.buttonAdditionTo10.setOnClickListener {
            startFlashCardsFor(FlashCards.Options.ADDITION_RESULTS_EQUAL_TO_10)
        }
        binding.buttonAdditionFrom10To20.setOnClickListener {
            startFlashCardsFor(FlashCards.Options.ADDITION_RESULTS_BETWEEN_10_AND_20)
        }
        binding.buttonMultiplicationTimesTablesTo10.setOnClickListener {
            startFlashCardsFor(FlashCards.Options.MULTIPLICATION_TIMES_TABLES_TO_10)
        }
        binding.buttonDivisionFactorsUpTo10.setOnClickListener {
            startFlashCardsFor(FlashCards.Options.DIVISION_FACTORS_UP_TO_10)
        }
    }

    private fun startFlashCardsFor(selectedOption: FlashCards.Options?) {
        startWith(this, selectedOption!!)
        finish()
    }
}
