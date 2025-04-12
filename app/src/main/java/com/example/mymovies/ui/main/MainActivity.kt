package com.example.mymovies.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mymovies.R
import com.example.mymovies.databinding.ActivityMainBinding
import com.example.mymovies.model.Movie
import com.example.mymovies.model.MovieDbClient
import com.example.mymovies.ui.detail.DetailActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val moviesAdapter = MoviesAdapter(emptyList()) { movie -> navigateTo(movie) }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            requestPopularMovies(isGranted)
            val message = when {
                isGranted -> "Permission Granted"
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> "Should show Rationale"
                else -> "Permission Denied"
            }
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        binding.recycler.adapter = moviesAdapter
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    @SuppressLint("MissingPermission")
    private fun requestPopularMovies(isLocationGranted: Boolean) {
        if (isLocationGranted) {
            fusedLocationClient.lastLocation.addOnCompleteListener {
                doRequestPopularMovies(getRegionFromLocation(it.result))
            }
        } else {
            doRequestPopularMovies("US")
        }
    }

    private fun doRequestPopularMovies(region: String) {
        lifecycleScope.launch {
            val apiKey = getString(R.string.api_key)
            val popularMovies = MovieDbClient.service.listPopularMovies(apiKey, region)
            moviesAdapter.movies = popularMovies.results
            moviesAdapter.notifyDataSetChanged()
        }
    }

    private fun getRegionFromLocation(location: Location): String {
        val geocoder = Geocoder(this)
        val result = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        return result?.firstOrNull()?.countryCode ?: "US"
    }

    private fun navigateTo(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie)
        startActivity(intent)
    }
}