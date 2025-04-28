package com.trios2025dj.dailyverse.utils

import android.content.Context
import com.trios2025dj.dailyverse.models.Quote
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object FavoritesManager {

    private const val PREFS_NAME = "favorites_prefs"
    private const val KEY_FAVORITES = "favorites_list"
    private val favorites = mutableListOf<Quote>()
    private val gson = Gson()

    private var isInitialized = false

    fun initialize(context: Context) {
        if (!isInitialized) {
            loadFavorites(context)
            isInitialized = true
        }
    }

    fun addFavorite(quote: Quote, context: Context? = null) {
        if (!favorites.any { it.text == quote.text }) {
            favorites.add(quote)
            context?.let { saveFavorites(it) }
        }
    }

    fun getFavorites(): List<Quote> {
        return favorites
    }

    private fun saveFavorites(context: Context) {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        val json = gson.toJson(favorites)
        editor.putString(KEY_FAVORITES, json)
        editor.apply()
    }

    private fun loadFavorites(context: Context) {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = sharedPrefs.getString(KEY_FAVORITES, null)
        if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<List<Quote>>() {}.type
            val loadedFavorites: List<Quote> = gson.fromJson(json, type)
            favorites.clear()
            favorites.addAll(loadedFavorites)
        }
    }

    fun removeFavorite(quote: Quote, context: Context? = null) {
        favorites.removeAll { it.text == quote.text }
        context?.let { saveFavorites(it) }
    }

    fun isFavorite(quote: Quote): Boolean {
        return favorites.any { it.text == quote.text }
    }

    fun toggleFavorite(quote: Quote, context: Context): Boolean {
        return if (isFavorite(quote)) {
            removeFavorite(quote, context)
            false // Now not a favorite
        } else {
            addFavorite(quote, context)
            true // Now added as favorite
        }
    }


}
