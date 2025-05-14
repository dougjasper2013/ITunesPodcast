package com.trios2025dj.itunespodcast.ui

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
import com.trios2025dj.itunespodcast.data.SubscriptionManager

class SubscriptionsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PodcastAdapter // Make sure PodcastAdapter is correctly implemented
    private val REQUEST_DETAIL = 1
    private lateinit var detailLauncher: ActivityResultLauncher<Intent>

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

        detailLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val updatedList = SubscriptionManager.getSubscriptions(this)
                adapter.updatePodcasts(updatedList)
            }
        }

        adapter = PodcastAdapter(subscriptions) { podcast ->
            val intent = Intent(this, PodcastDetailActivity::class.java)
            intent.putExtra("title", podcast.collectionName)
            intent.putExtra("artist", podcast.artistName)
            intent.putExtra("artworkUrl", podcast.artworkUrl100)
            intent.putExtra("feedUrl", podcast.feedUrl)
            detailLauncher.launch(intent)
        }

        recyclerView.adapter = adapter


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1001 && resultCode == RESULT_OK) {
            val updatedList = SubscriptionManager.getSubscriptions(this)
            adapter.updatePodcasts(updatedList)
        }
    }

    override fun onResume() {
        super.onResume()

        // Reload subscriptions every time activity resumes
        val subscriptions = SubscriptionManager.getSubscriptions(this)
        adapter.updateData(subscriptions)
    }


}