package com.trios2025dj.itunespodcast.ui

import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.material.appbar.MaterialToolbar
import com.trios2025dj.itunespodcast.R

class EpisodeDetailActivity : AppCompatActivity() {

    private var exoPlayer: ExoPlayer? = null
    private lateinit var playerView: PlayerView

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

        // Set episode info
        findViewById<TextView>(R.id.textViewEpisodeTitle).text = title
        findViewById<TextView>(R.id.textViewEpisodeDate).text = pubDate
        findViewById<TextView>(R.id.textViewEpisodeDescription).text =
            HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY)

        // Setup ExoPlayer
        playerView = findViewById(R.id.playerView)
        initializePlayer(audioUrl)
    }

    private fun initializePlayer(url: String) {
        exoPlayer = ExoPlayer.Builder(this).build().also { player ->
            playerView.player = player
            val mediaItem = MediaItem.fromUri(Uri.parse(url))
            player.setMediaItem(mediaItem)
            player.prepare()
            player.play()
        }
    }

    override fun onStop() {
        super.onStop()
        exoPlayer?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.release()
        exoPlayer = null
    }
}