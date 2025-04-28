package com.trios2025dj.dailyverse.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.trios2025dj.dailyverse.R
import com.trios2025dj.dailyverse.models.Quote
import com.trios2025dj.dailyverse.utils.FavoritesManager

class QuoteAdapter(
    private val quotes: List<Quote>,
    private val showFavoriteButton: Boolean
) : RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {

    inner class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quoteText: TextView = itemView.findViewById(R.id.quote_text)
        val quoteAuthor: TextView = itemView.findViewById(R.id.quote_author)
        val favoriteButton: Button = itemView.findViewById(R.id.favorite_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_quote, parent, false)
        return QuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quote = quotes[position]
        holder.quoteText.text = "\"${quote.text}\""
        holder.quoteAuthor.text = "- ${quote.author}"

        if (showFavoriteButton) {
            holder.favoriteButton.visibility = View.VISIBLE
            holder.favoriteButton.setOnClickListener {
                FavoritesManager.addFavorite(quote)
                Toast.makeText(holder.itemView.context, "Added to favorites!", Toast.LENGTH_SHORT).show()
            }
        } else {
            holder.favoriteButton.visibility = View.GONE
        }
    }

    override fun getItemCount() = quotes.size
}