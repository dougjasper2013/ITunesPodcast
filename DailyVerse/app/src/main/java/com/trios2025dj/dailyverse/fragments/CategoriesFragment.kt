package com.trios2025dj.dailyverse.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trios2025dj.dailyverse.R
import com.trios2025dj.dailyverse.adapters.CategoryAdapter
import com.trios2025dj.dailyverse.adapters.QuoteAdapter
import com.trios2025dj.dailyverse.models.Quote
import com.trios2025dj.dailyverse.utils.QuoteRepository

class CategoriesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var showingCategories = true
    private val categories = listOf("Wisdom", "Success", "Love", "Motivation")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categories, container, false)
        recyclerView = view.findViewById(R.id.categories_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showCategories()
        return view
    }

    private fun showCategories() {
        showingCategories = true
        recyclerView.adapter = CategoryAdapter(categories) { selectedCategory ->
            showQuotesByCategory(selectedCategory)
        }
    }

    private fun showQuotesByCategory(category: String) {
        showingCategories = false
        val quotes = QuoteRepository.getQuotesByCategory(requireContext(), category)
        recyclerView.adapter = QuoteAdapter(quotes)
    }
}
