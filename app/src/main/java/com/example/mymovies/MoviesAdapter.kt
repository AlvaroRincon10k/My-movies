package com.example.mymovies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.databinding.ViewMovieItemBinding

class MoviesAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bing(movies[position])
    }

    class ViewHolder(private val binding: ViewMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bing(movie: Movie) {
            binding.title.text = movie.title
        }
    }
}