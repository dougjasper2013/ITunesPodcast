package com.trios2025dj.itunespodcast.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.google.android.material.appbar.MaterialToolbar
import com.trios2025dj.itunespodcast.R
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar

class EpisodeDetailActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    private lateinit var buttonPlayPause: Button
    private lateinit var seekBar: SeekBar
    private lateinit var textViewCurrentTime: TextView
    private lateinit var textViewTotalTime: TextView

    private val handler = Handler(Looper.getMainLooper())
    private val updateSeekBarRunnable = object : Runnable {
        override fun run() {
            mediaPlayer?.let { mp ->
                val currentPos = mp.currentPosition
                seekBar.progress = currentPos
                textViewCurrentTime.text = formatTime(currentPos)
                handler.postDelayed(this, 1000)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode_detail)

        // Toolbar back
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Get data
        val title = intent.getStringExtra("title") ?: ""
        val pubDate = intent.getStringExtra("pubDate") ?: ""
        val description = intent.getStringExtra("description") ?: ""
        val audioUrl = intent.getStringExtra("audioUrl") ?: ""
        toolbar.title = "Episode Detail"

        // Views
        val textViewTitle: TextView = findViewById(R.id.textViewEpisodeTitle)
        val textViewDate: TextView = findViewById(R.id.textViewEpisodeDate)
        val textViewDescription: TextView = findViewById(R.id.textViewEpisodeDescription)
        buttonPlayPause = findViewById(R.id.buttonPlayPause)
        seekBar = findViewById(R.id.seekBar)
        textViewCurrentTime = findViewById(R.id.textViewCurrentTime)
        textViewTotalTime = findViewById(R.id.textViewTotalTime)

        textViewTitle.text = title
        textViewDate.text = pubDate
        textViewDescription.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY)

        buttonPlayPause.setOnClickListener {
            if (!isPlaying) {
                playAudio(audioUrl)
            } else {
                pauseAudio()
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) { }
            override fun onStopTrackingTouch(seekBar: SeekBar?) { }
        })
    }

    private fun playAudio(url: String) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(url)
                prepareAsync()
                setOnPreparedListener { mp ->
                    seekBar.max = mp.duration
                    textViewTotalTime.text = formatTime(mp.duration)

                    mp.start()
                    this@EpisodeDetailActivity.isPlaying = true
                    buttonPlayPause.text = "Pause"

                    handler.post(updateSeekBarRunnable)
                }
            }
        } else {
            mediaPlayer?.start()
            isPlaying = true
            buttonPlayPause.text = "Pause"
            handler.post(updateSeekBarRunnable)
        }
    }

    private fun pauseAudio() {
        mediaPlayer?.pause()
        isPlaying = false
        buttonPlayPause.text = "Play"
        handler.removeCallbacks(updateSeekBarRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
        handler.removeCallbacks(updateSeekBarRunnable)
    }

    private fun formatTime(milliseconds: Int): String {
        val totalSeconds = milliseconds / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}