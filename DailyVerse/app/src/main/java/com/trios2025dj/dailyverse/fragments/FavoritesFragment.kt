package com.trios2025dj.dailyverse.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trios2025dj.dailyverse.R
import com.trios2025dj.dailyverse.adapters.QuoteAdapter
import com.trios2025dj.dailyverse.utils.FavoritesManager

class FavoritesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        recyclerView = view.findViewById(R.id.favorites_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = QuoteAdapter(FavoritesManager.getFavorites(), showFavoriteButton = false)

        return view
    }
}
