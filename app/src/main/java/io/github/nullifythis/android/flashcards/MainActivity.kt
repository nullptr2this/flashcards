package io.github.nullifythis.android.flashcards

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.nullifythis.android.flashcards.FlashCardsActivity.Companion.startWith
import io.github.nullifythis.android.flashcards.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupForFullScreen()
        binding.buttonLetters.setOnClickListener {
            startWith(this@MainActivity, FlashCards.Options.LETTERS_ALL_IN_ORDER_LOWER_CASE)
        }
        binding.buttonNumbers.setOnClickListener {
        }
        binding.buttonSightWords.setOnClickListener {
            this@MainActivity.startActivity(Intent(this@MainActivity, WordsSelectionActivity::class.java))
        }
        binding.buttonSpellingWords.setOnClickListener {
        }
        binding.buttonMath.setOnClickListener {
            this@MainActivity.startActivity(Intent(this@MainActivity, MathSelectionActivity::class.java))
        }
        binding.buttonTime.setOnClickListener {
        }
    }
}