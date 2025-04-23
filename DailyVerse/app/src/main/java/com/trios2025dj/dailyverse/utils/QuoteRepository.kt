package com.trios2025dj.dailyverse.utils

import android.content.Context
import com.trios2025dj.dailyverse.R
import com.trios2025dj.dailyverse.models.Quote
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader

object QuoteRepository {
    private var cachedQuotes: List<Quote>? = null

    fun getRandomQuote(context: Context): Quote {
        if (cachedQuotes == null) loadQuotes(context)
        return cachedQuotes!!.random()
    }

    fun loadQuotes(context: Context) {
        val inputStream = context.resources.openRawResource(R.raw.quotes)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val json = reader.use { it.readText() }

        val array = JSONArray(json)
        val list = mutableListOf<Quote>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            list.add(
                Quote(
                    id = i,
                    text = obj.getString("text"),
                    author = obj.getString("author"),
                    category = obj.getString("category")
                )
            )
        }
        cachedQuotes = list
    }

    fun getQuotesByCategory(context: Context, category: String): List<Quote> {
        if (cachedQuotes == null) loadQuotes(context)
        return cachedQuotes!!.filter { it.category.equals(category, ignoreCase = true) }
    }
}
