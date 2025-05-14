package com.trios2025dj.itunespodcast.ui

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
            // You could open detail or do nothing — up to you
        }
        recyclerView.adapter = adapter
    }


}