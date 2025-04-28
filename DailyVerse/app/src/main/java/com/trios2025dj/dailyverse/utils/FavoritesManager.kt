package com.trios2025dj.dailyverse.utils

import com.trios2025dj.dailyverse.models.Quote

object FavoritesManager {
    private val favorites = mutableListOf<Quote>()

    fun addFavorite(quote: Quote) {
        if (!favorites.any { it.text == quote.text }) {
            favorites.add(quote)
        }
    }

    fun getFavorites(): List<Quote> {
        return favorites
    }
}