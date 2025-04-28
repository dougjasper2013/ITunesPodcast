package com.trios2025dj.dailyverse.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.trios2025dj.dailyverse.R
import com.trios2025dj.dailyverse.models.Quote
import com.trios2025dj.dailyverse.utils.FavoritesManager
import com.trios2025dj.dailyverse.utils.QuoteRepository

class HomeFragment : Fragment() {
    private lateinit var quote: Quote

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val quoteText = view.findViewById<TextView>(R.id.daily_quote_text)
        val favoriteButton = view.findViewById<Button>(R.id.favorite_button)

        quote = QuoteRepository.getRandomQuote(requireContext())
        quoteText.text = "\"${quote.text}\"\n\n- ${quote.author}"

        favoriteButton.setOnClickListener {
            FavoritesManager.addFavorite(quote)
            Toast.makeText(requireContext(), "Added to favorites!", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
