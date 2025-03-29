package com.example.mymovies

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mymovies.databinding.ActivityMainBinding
import com.example.mymovies.model.MovieDbClient
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.adapter = MoviesAdapter(
            listOf(
                Movie("Title 1", "https://loremflickr.com/320/240?lock=1"),
                Movie("Title 2", "https://loremflickr.com/320/240?lock=2"),
                Movie("Title 3", "https://loremflickr.com/320/240?lock=3"),
                Movie("Title 4", "https://loremflickr.com/320/240?lock=4"),
                Movie("Title 5", "https://loremflickr.com/320/240?lock=5"),
                Movie("Title 6", "https://loremflickr.com/320/240?lock=6")
            )
        ) { movie ->
            Toast.makeText(this@MainActivity, movie.title, Toast.LENGTH_SHORT).show()
        }

        thread {
            val apiKey = getString(R.string.api_key)
            val popularMovies = MovieDbClient.service.listPopularMovies(apiKey)
            val body = popularMovies.execute().body()
            if (body != null)
                Log.d("Alvaro", "Movie count: ${body.results.size}")
        }
    }
}