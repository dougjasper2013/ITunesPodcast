package com.trios2025dj.itunespodcast.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trios2025dj.itunespodcast.R
import com.trios2025dj.itunespodcast.data.ITunesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var editTextSearch: EditText
    private lateinit var buttonSearch: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PodcastAdapter
    private lateinit var api: ITunesApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init Views
        editTextSearch = findViewById(R.id.editTextSearch)
        buttonSearch = findViewById(R.id.buttonSearch)
        recyclerView = findViewById(R.id.recyclerView)

        // Setup RecyclerView
        adapter = PodcastAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Init Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(ITunesApi::class.java)

        // Handle Search Button Click
        buttonSearch.setOnClickListener {
            val query = editTextSearch.text.toString().trim()
            if (query.isNotEmpty()) {
                searchPodcasts(query)
            }
        }
    }

    private fun searchPodcasts(query: String) {
        lifecycleScope.launch {
            try {
                val response = api.searchPodcasts(query)
                adapter.updateList(response.results)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}