package com.trios2025dj.itunespodcast.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.trios2025dj.itunespodcast.R
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trios2025dj.itunespodcast.data.Episode
import com.trios2025dj.itunespodcast.data.Podcast
import com.trios2025dj.itunespodcast.data.SubscriptionManager
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.net.URL

class PodcastDetailActivity : AppCompatActivity() {

    private lateinit var subscribeButton: Button
    private lateinit var podcastId: String
    private var subscriptionChanged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_podcast_detail)

        subscribeButton = findViewById(R.id.buttonSubscribe)

        updateSubscribeButton()

        subscribeButton.setOnClickListener {
            val podcast = Podcast(
                collectionName = intent.getStringExtra("title") ?: "",
                trackName = intent.getStringExtra("title") ?: "",
                artistName = intent.getStringExtra("artist") ?: "",
                artworkUrl100 = intent.getStringExtra("artworkUrl") ?: "",
                feedUrl = intent.getStringExtra("feedUrl") ?: "",
                trackId = intent.getStringExtra("feedUrl")?.hashCode()?.toLong() ?: 0L
            )

            val isSubscribed = SubscriptionManager.isSubscribed(this, podcast.trackId)

            if (isSubscribed) {
                SubscriptionManager.removeSubscription(this, podcast.trackId)
                Toast.makeText(this, "Unsubscribed", Toast.LENGTH_SHORT).show()
            } else {
                SubscriptionManager.addSubscription(this, podcast)
                Toast.makeText(this, "Subscribed", Toast.LENGTH_SHORT).show()
            }

            subscriptionChanged = true
            updateSubscribeButton()
        }



        // Set up the Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

// Enable the back arrow (up button)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

// Handle back arrow click
        toolbar.setNavigationOnClickListener {
            if (subscriptionChanged) {
                setResult(RESULT_OK)
            }
            finish() // Acts like back button
        }

        // Get views
        val imageArtwork: ImageView = findViewById(R.id.imageViewDetailArtwork)
        val textTitle: TextView = findViewById(R.id.textViewDetailTitle)
        val textArtist: TextView = findViewById(R.id.textViewDetailArtist)
        val textFeedUrl: TextView = findViewById(R.id.textViewDetailFeedUrl)

        // Get data from Intent
        val title = intent.getStringExtra("title") ?: ""
        supportActionBar?.title = title
        val artist = intent.getStringExtra("artist") ?: ""
        val artworkUrl = intent.getStringExtra("artworkUrl") ?: ""
        val feedUrl = intent.getStringExtra("feedUrl") ?: ""

        // Get back button view
        //val buttonBack: ImageButton = findViewById(R.id.buttonBack)

        //buttonBack.setOnClickListener {
        //    finish()
        //}

        // Set data
        textTitle.text = title
        textArtist.text = artist
        textFeedUrl.text = feedUrl

        Glide.with(this)
            .load(artworkUrl)
            .into(imageArtwork)

        // Optional: make feedUrl clickable later

        val recyclerViewEpisodes: RecyclerView = findViewById(R.id.recyclerViewEpisodes)
        recyclerViewEpisodes.layoutManager = LinearLayoutManager(this)

// Get feed URL passed from intent
        //val feedUrl = intent.getStringExtra("feedUrl")

// Load episodes
        if (feedUrl.isNotEmpty()) {
            loadEpisodes(feedUrl) { episodes ->
                recyclerViewEpisodes.adapter = EpisodeAdapter(episodes) { episode ->
                    val intent = Intent(this, EpisodeDetailActivity::class.java)
                    intent.putExtra("title", episode.title)
                    intent.putExtra("pubDate", episode.pubDate)
                    intent.putExtra("description", episode.description)
                    intent.putExtra("audioUrl", episode.audioUrl)
                    startActivity(intent)
                }
            }
        }



    }

    override fun onBackPressed() {
        if (subscriptionChanged) {
            setResult(RESULT_OK)
        }
        super.onBackPressed()
    }

    override fun finish() {
        if (subscriptionChanged) {
            setResult(RESULT_OK)
        }
        super.finish()
    }

    private fun loadEpisodes(feedUrl: String, callback: (List<Episode>) -> Unit) {
        Thread {
            try {
                val url = URL(feedUrl)
                val connection = url.openConnection()
                val inputStream = connection.getInputStream()

                val episodes = parseEpisodes(inputStream)

                runOnUiThread {
                    callback(episodes)
                }

                inputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    callback(emptyList())
                }
            }
        }.start()
    }


    private fun parseEpisodes(inputStream: InputStream): List<Episode> {
        val episodes = mutableListOf<Episode>()

        val factory = XmlPullParserFactory.newInstance()
        val parser = factory.newPullParser()
        parser.setInput(inputStream, null)

        var eventType = parser.eventType
        var insideItem = false
        var title = ""
        var pubDate = ""
        var audioUrl = ""
        var description = ""

        while (eventType != XmlPullParser.END_DOCUMENT) {
            val tagName = parser.name

            when (eventType) {
                XmlPullParser.START_TAG -> {
                    if (tagName.equals("item", ignoreCase = true)) {
                        insideItem = true
                    } else if (insideItem) {
                        when {
                            tagName.equals("title", ignoreCase = true) -> title = parser.nextText()
                            tagName.equals("pubDate", ignoreCase = true) -> pubDate = parser.nextText()
                            tagName.equals("enclosure", ignoreCase = true) -> audioUrl = parser.getAttributeValue(null, "url") ?: ""
                            tagName.equals("description", ignoreCase = true) -> description = parser.nextText()
                        }
                    }
                }

                XmlPullParser.END_TAG -> {
                    if (tagName.equals("item", ignoreCase = true)) {
                        episodes.add(Episode(title, pubDate, audioUrl, description))
                        // Reset
                        title = ""
                        pubDate = ""
                        audioUrl = ""
                        description = ""
                        insideItem = false
                    }
                }
            }

            eventType = parser.next()
        }

        return episodes
    }

    private fun toggleSubscription(id: String) {
        val prefs = getSharedPreferences("subscriptions", MODE_PRIVATE)
        val isSubscribed = prefs.getBoolean(id, false)

        prefs.edit().putBoolean(id, !isSubscribed).apply()
        updateSubscribeButton()
    }

    private fun updateSubscribeButton() {
        val isSubscribed = SubscriptionManager.isSubscribed(
            this,
            intent.getStringExtra("feedUrl")?.hashCode()?.toLong() ?: 0L
        )
        subscribeButton.text = if (isSubscribed) "Unsubscribe" else "Subscribe"
    }

}