package com.trios2025dj.itunespodcast.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.trios2025dj.itunespodcast.R
import com.trios2025dj.itunespodcast.data.Episode

class EpisodeAdapter(private val episodes: List<Episode>) : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    inner class EpisodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewEpisodeTitle)
        val textViewDate: TextView = itemView.findViewById(R.id.textViewEpisodeDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_episode, parent, false)
        return EpisodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = episodes[position]
        holder.textViewTitle.text = episode.title
        holder.textViewDate.text = episode.pubDate
    }

    override fun getItemCount(): Int = episodes.size
}