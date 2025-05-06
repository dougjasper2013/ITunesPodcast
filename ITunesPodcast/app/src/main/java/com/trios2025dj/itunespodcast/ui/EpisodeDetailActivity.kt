package com.trios2025dj.itunespodcast.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.google.android.material.appbar.MaterialToolbar
import com.trios2025dj.itunespodcast.R

class EpisodeDetailActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    private lateinit var buttonPlayPause: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode_detail)

        // Toolbar back button
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Get data
        val title = intent.getStringExtra("title") ?: ""
        val pubDate = intent.getStringExtra("pubDate") ?: ""
        val description = intent.getStringExtra("description") ?: ""
        val audioUrl = intent.getStringExtra("audioUrl") ?: ""

        // Set views
        val textViewTitle: TextView = findViewById(R.id.textViewEpisodeTitle)
        val textViewDate: TextView = findViewById(R.id.textViewEpisodeDate)
        val textViewDescription: TextView = findViewById(R.id.textViewEpisodeDescription)
        buttonPlayPause = findViewById(R.id.buttonPlayPause)

        textViewTitle.text = title
        textViewDate.text = pubDate
        textViewDescription.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY)

        // Play button logic
        buttonPlayPause.setOnClickListener {
            if (!isPlaying) {
                playAudio(audioUrl)
            } else {
                pauseAudio()
            }
        }
    }

    private fun playAudio(url: String) {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener {
                start()
                this@EpisodeDetailActivity.isPlaying = true
                buttonPlayPause.text = "Pause"
            }
        }
    }

    private fun pauseAudio() {
        mediaPlayer?.pause()
        isPlaying = false
        buttonPlayPause.text = "Play"
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}