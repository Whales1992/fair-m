package com.android.fairmoney.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.whales.carbontest.R
import com.whales.carbontest.networks.rectrofit.dto.MovieDTO

class MovieListAdapter(private val movieList: ArrayList<MovieDTO.Result>, private val actions : MovieActions) : RecyclerView.Adapter<MovieListAdapter.MovieListAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListAdapterViewHolder {
        return MovieListAdapterViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun onBindViewHolder(holder: MovieListAdapterViewHolder, position: Int) {
        val data: MovieDTO.Result = movieList[position]
        holder.bind(data, actions)
    }

    override fun getItemCount(): Int {
        return movieList.size;
    }

    class MovieListAdapterViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_movie_layout, parent, false))
    {
        private var parentLayout: ConstraintLayout = itemView.findViewById(R.id.parentLayout)
        private var dpCardView: CardView = itemView.findViewById(R.id.dpCardView)
        private var dpView: ImageView = itemView.findViewById(R.id.imageView)
        private var movieNameTextView: TextView = itemView.findViewById(R.id.movieNameTextView)

        fun bind(movie: MovieDTO.Result, actions: MovieActions) {
            actions.renderItem(parentLayout, dpCardView, dpView, movieNameTextView, movie)
        }
    }
}