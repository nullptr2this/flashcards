package io.github.nullifythis.android.flashcards

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_launch.*

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        setupForFullScreen()

        button_new_words.setOnClickListener {
            startFlashCardsFor(Words.Options.WORDS_NEW)
        }
        button_all_words.setOnClickListener {
            startFlashCardsFor(Words.Options.WORDS_ALL)
        }
        button_letters_ordered.setOnClickListener {
            startFlashCardsFor(Words.Options.LETTERS_ORDERED)
        }
        button_letters_random.setOnClickListener {
            startFlashCardsFor(Words.Options.LETTERS_RANDOM)
        }
    }

    private fun startFlashCardsFor(selectedOption: Words.Options) {
        startActivity(MainActivity.intentFor(this, selectedOption))
    }
}
