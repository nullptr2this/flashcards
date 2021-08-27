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
            startFlashCardsFor(Words.Options.GROUP_1)
        }
        button_all_words.setOnClickListener {
            startFlashCardsFor(Words.Options.GROUP_2)
        }
        button_letters_ordered.setOnClickListener {
            startFlashCardsFor(Words.Options.GROUP_3)
        }
        button_letters_random.setOnClickListener {
            startFlashCardsFor(Words.Options.GROUP_4)
        }
    }

    private fun startFlashCardsFor(selectedOption: Words.Options) {
        startActivity(MainActivity.intentFor(this, selectedOption))
    }
}
