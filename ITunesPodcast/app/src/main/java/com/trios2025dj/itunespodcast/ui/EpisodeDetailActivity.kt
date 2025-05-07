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
import android.content.pm.ActivityInfo
import android.view.MotionEvent
import android.view.View
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerControlView

class EpisodeDetailActivity : AppCompatActivity() {

    private var exoPlayer: ExoPlayer? = null
    private lateinit var playerView: PlayerView

    private var isFullscreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode_detail)

        playerView = findViewById(R.id.playerView)

        playerView.setUseController(true)             // ✅ Must be true
        playerView.setControllerShowTimeoutMs(3000)   // Auto-hide after 3 sec
        playerView.setControllerHideOnTouch(true)     // ✅ Tapping will toggle controls
        playerView.showController()

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


        initializePlayer(audioUrl)



        // Find the fullscreen button in the control view
        val fullscreenButton = playerView.findViewById<View>(com.google.android.exoplayer2.ui.R.id.exo_fullscreen)

// Set click listener to toggle fullscreen
        fullscreenButton?.setOnClickListener {
            toggleFullscreen()
        }
    }

    private fun initializePlayer(url: String) {
        exoPlayer = SimpleExoPlayer.Builder(this).build().also { player ->
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

    private fun toggleFullscreen() {
        if (isFullscreen) {
            // Exit fullscreen
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            isFullscreen = false
        } else {
            // Enter fullscreen
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            isFullscreen = true
        }
    }
}