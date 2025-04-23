package com.trios2025dj.dailyverse.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.trios2025dj.dailyverse.R

class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val quoteText = view.findViewById<TextView>(R.id.daily_quote_text)
        quoteText.text = QuoteRepository.getRandomQuote(requireContext()).text
        return view
    }
}
