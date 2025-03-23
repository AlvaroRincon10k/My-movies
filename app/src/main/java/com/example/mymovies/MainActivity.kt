package com.example.mymovies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mymovies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.adapter = MoviesAdapter(
            listOf(
                Movie("Title 1", "url 1"),
                Movie("Title 2", "url 2"),
                Movie("Title 3", "url 3"),
                Movie("Title 4", "url 4"),
                Movie("Title 5", "url 5"),
                Movie("Title 6", "url 6")
            )
        )
    }
}