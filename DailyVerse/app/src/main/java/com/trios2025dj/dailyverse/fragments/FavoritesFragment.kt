package com.trios2025dj.dailyverse.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trios2025dj.dailyverse.R
import com.trios2025dj.dailyverse.adapters.QuoteAdapter
import com.trios2025dj.dailyverse.utils.FavoritesManager

class FavoritesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        recyclerView = view.findViewById(R.id.favorites_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = QuoteAdapter(
            FavoritesManager.getFavorites(),
            showFavoriteButton = false
        ) { quote ->
            FavoritesManager.removeFavorite(quote, requireContext())
            Toast.makeText(requireContext(), "Removed from favorites.", Toast.LENGTH_SHORT).show()
            refreshList()
        }

        recyclerView.adapter = adapter

        Toast.makeText(requireContext(), "Tap a quote to remove it from Favorites.", Toast.LENGTH_SHORT).show()

        return view
    }

    private fun refreshList() {
        adapter = QuoteAdapter(
            FavoritesManager.getFavorites(),
            showFavoriteButton = false
        ) { quote ->
            FavoritesManager.removeFavorite(quote, requireContext())
            Toast.makeText(requireContext(), "Removed from favorites.", Toast.LENGTH_SHORT).show()
            refreshList()
        }
        recyclerView.adapter = adapter
    }
}
