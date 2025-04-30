package com.trios2025dj.itunespodcast.ui

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.trios2025dj.itunespodcast.R

class PodcastDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_podcast_detail)

        // Get views
        val imageArtwork: ImageView = findViewById(R.id.imageViewDetailArtwork)
        val textTitle: TextView = findViewById(R.id.textViewDetailTitle)
        val textArtist: TextView = findViewById(R.id.textViewDetailArtist)
        val textFeedUrl: TextView = findViewById(R.id.textViewDetailFeedUrl)

        // Get data from Intent
        val title = intent.getStringExtra("title") ?: ""
        val artist = intent.getStringExtra("artist") ?: ""
        val artworkUrl = intent.getStringExtra("artworkUrl") ?: ""
        val feedUrl = intent.getStringExtra("feedUrl") ?: ""

        // Get back button view
        val buttonBack: ImageButton = findViewById(R.id.buttonBack)

        buttonBack.setOnClickListener {
            finish()
        }

        // Set data
        textTitle.text = title
        textArtist.text = artist
        textFeedUrl.text = feedUrl

        Glide.with(this)
            .load(artworkUrl)
            .into(imageArtwork)

        // Optional: make feedUrl clickable later



    }
}