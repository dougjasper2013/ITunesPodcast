package com.trios2025dj.dailyverse.fragments

import android.os.Bundle
import android.view.*
import android.widget.Button
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
    private lateinit var backButton: Button
    private val categories = listOf("Wisdom", "Success", "Love", "Motivation")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_categories, container, false)

        recyclerView = view.findViewById(R.id.categories_recycler_view)
        backButton = view.findViewById(R.id.btn_back_to_categories)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        backButton.setOnClickListener {
            showCategories()
        }

        showCategories()
        return view
    }

    private fun showCategories() {
        backButton.visibility = View.GONE
        recyclerView.adapter = CategoryAdapter(categories) { selectedCategory ->
            showQuotesByCategory(selectedCategory)
        }
    }

    private fun showQuotesByCategory(category: String) {
        backButton.visibility = View.VISIBLE
        val quotes = QuoteRepository.getQuotesByCategory(requireContext(), category)
        recyclerView.adapter = QuoteAdapter(quotes, showFavoriteButton = true)
    }

}