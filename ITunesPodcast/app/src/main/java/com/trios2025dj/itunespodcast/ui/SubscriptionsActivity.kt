package com.trios2025dj.itunespodcast.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.trios2025dj.itunespodcast.R
import com.trios2025dj.itunespodcast.data.Podcast
import com.trios2025dj.itunespodcast.data.SubscriptionManager

class SubscriptionsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PodcastAdapter
    private lateinit var detailLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscriptions)

        val toolbar: MaterialToolbar = findViewById(R.id.subscriptionsToolbar)
        recyclerView = findViewById(R.id.recyclerViewSubscriptions)
        recyclerView.layoutManager = LinearLayoutManager(this)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Subscriptions"
        toolbar.setNavigationOnClickListener { finish() }

        // Register the activity result launcher
        detailLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    Log.d("SubscriptionsActivity", "ActivityResult received with code=${result.resultCode}")
                    updateSubscriptions()
                }
            }

        updateSubscriptions()
    }

    private fun updateSubscriptions() {
        val subscriptions: List<Podcast> = SubscriptionManager.getSubscriptions(this)
        Log.d("SubscriptionsActivity", "Updated subscriptions: $subscriptions")

        adapter = PodcastAdapter(subscriptions) { podcast ->
            val intent = Intent(this, PodcastDetailActivity::class.java).apply {
                putExtra("title", podcast.collectionName)
                putExtra("artist", podcast.artistName)
                putExtra("artworkUrl", podcast.artworkUrl100)
                putExtra("feedUrl", podcast.feedUrl)
            }
            detailLauncher.launch(intent)
        }

        recyclerView.adapter = adapter
    }
}