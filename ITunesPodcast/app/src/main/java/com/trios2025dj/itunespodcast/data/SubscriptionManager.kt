package com.trios2025dj.itunespodcast.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.trios2025dj.itunespodcast.data.Podcast

object SubscriptionManager {
    private const val PREFS_NAME = "subscriptions_prefs"
    private const val KEY_SUBSCRIPTIONS = "subscriptions"
    private val gson = Gson()

    fun getSubscriptions(context: Context): List<Podcast> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_SUBSCRIPTIONS, null)
        Log.d("SubscriptionManager", "getSubscriptions() called: $json")
        return if (json != null) {
            val type = object : TypeToken<List<Podcast>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun addSubscription(context: Context, podcast: Podcast) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val subscriptions = getSubscriptions(context).toMutableList()
        if (!subscriptions.any { it.trackId == podcast.trackId }) {
            subscriptions.add(podcast)
            prefs.edit().putString(KEY_SUBSCRIPTIONS, gson.toJson(subscriptions)).apply()
            Log.d("SubscriptionManager", "Added subscription: ${podcast.trackId}")
        }
    }

    fun removeSubscription(context: Context, trackId: Long) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val subscriptions = getSubscriptions(context).toMutableList()
        val removed = subscriptions.removeAll { it.trackId == trackId }
        prefs.edit().putString(KEY_SUBSCRIPTIONS, gson.toJson(subscriptions)).apply()
        Log.d("SubscriptionManager", "Removed subscription $trackId: success=$removed")
    }

    fun isSubscribed(context: Context, podcastId: Long): Boolean {
        return getSubscriptions(context).any { it.trackId == podcastId }
    }

    private fun saveSubscriptions(context: Context, subscriptions: List<Podcast>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_SUBSCRIPTIONS, gson.toJson(subscriptions)).apply()
    }
}