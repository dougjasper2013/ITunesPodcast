package com.trios2025dj.itunespodcast.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.trios2025dj.itunespodcast.R
import com.trios2025dj.itunespodcast.data.Podcast

class SubscriptionsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PodcastAdapter // Make sure PodcastAdapter is correctly implemented

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscriptions)

        // Back button handling
        val toolbar = findViewById<MaterialToolbar>(R.id.subscriptionsToolbar)
        toolbar.setNavigationOnClickListener { finish() }

        // Load subscriptions
        val subscriptions = loadSubscribedPodcasts()

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

    private fun loadSubscribedPodcasts(): List<Podcast> {
        val prefs = getSharedPreferences("subscriptions", Context.MODE_PRIVATE)
        val jsonSet = prefs.getStringSet("podcast_list", emptySet()) ?: emptySet()
        val gson = Gson()

        return jsonSet.mapNotNull { json ->
            try {
                gson.fromJson(json, Podcast::class.java)
            } catch (e: Exception) {
                null
            }
        }
    }
}