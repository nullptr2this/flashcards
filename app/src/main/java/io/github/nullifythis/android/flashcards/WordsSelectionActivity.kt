package io.github.nullifythis.android.flashcards

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.nullifythis.android.flashcards.FlashCardsActivity.Companion.startWith
import io.github.nullifythis.android.flashcards.databinding.ActivityWordsSelectionBinding

class WordsSelectionActivity : AppCompatActivity() {

    private val binding: ActivityWordsSelectionBinding by lazy {
        ActivityWordsSelectionBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        this.setupForFullScreen()
        binding.buttonGroup1.setOnClickListener {
            startFlashCardsFor(
                FlashCards.Options.SIGHT_WORD_GROUP_FRYS_1ST
            )
        }
        binding.buttonGroup2.setOnClickListener {
            startFlashCardsFor(
                FlashCards.Options.SIGHT_WORD_GROUP_FRYS_2ND
            )
        }
        binding.buttonGroup3.setOnClickListener {
            startFlashCardsFor(
                FlashCards.Options.SIGHT_WORD_GROUP_FRYS_3RD
            )
        }
        binding.buttonGroup4.setOnClickListener {
            startFlashCardsFor(
                FlashCards.Options.SIGHT_WORD_GROUP_FRYS_4TH
            )
        }
    }

    private fun startFlashCardsFor(selectedOption: FlashCards.Options?) {
        startWith(this, selectedOption!!)
        finish()
    }
}