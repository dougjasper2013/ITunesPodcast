package com.trios2025dj.itunespodcast.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.trios2025dj.itunespodcast.R
import com.trios2025dj.itunespodcast.data.SubscriptionManager

class SubscriptionsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PodcastAdapter // Make sure PodcastAdapter is correctly implemented

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscriptions)

        // Back button handling
        val toolbar = findViewById<MaterialToolbar>(R.id.subscriptionsToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Subscriptions"
        toolbar.setNavigationOnClickListener { finish() }

        // Load subscriptions
        val subscriptions  = SubscriptionManager.getSubscriptions(this)

        // Log the subscriptions value
        Log.d("SubscriptionsActivity", "Subscriptions: $subscriptions")

        // RecyclerView setup
        recyclerView = findViewById(R.id.recyclerViewSubscriptions)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PodcastAdapter(subscriptions) { podcast ->
            val intent = Intent(this, PodcastDetailActivity::class.java)
            intent.putExtra("title", podcast.collectionName)
            intent.putExtra("artist", podcast.artistName)
            intent.putExtra("artworkUrl", podcast.artworkUrl100)
            intent.putExtra("feedUrl", podcast.feedUrl)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }


}