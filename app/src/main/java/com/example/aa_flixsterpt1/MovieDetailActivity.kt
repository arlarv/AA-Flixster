package com.example.aa_flixsterpt1

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val moviePosterImageView = findViewById<ImageView>(R.id.imageView)
        val budgetTextView = findViewById<TextView>(R.id.movie_budgetTV)
        val releaseDateTextView = findViewById<TextView>(R.id.movie_release_dateTV)
        val taglineTextView = findViewById<TextView>(R.id.movie_taglineTV)
        val descriptionTextView = findViewById<TextView>(R.id.movie_descriptionTV)

        val movieTitle = intent.getStringExtra(NowPlayingMoviesRecyclerViewAdapter.MOVIE_TITLE_KEY)
        val moviePoster = intent.getStringExtra(NowPlayingMoviesRecyclerViewAdapter.MOVIE_POSTER_KEY)
        val budget = intent.getDoubleExtra(NowPlayingMoviesRecyclerViewAdapter.BUDGET_KEY, 0.0)
        val releaseDate = intent.getStringExtra(NowPlayingMoviesRecyclerViewAdapter.RELEASE_DATE_KEY)
        val tagline = intent.getStringExtra(NowPlayingMoviesRecyclerViewAdapter.TAGLINE_KEY)
        val movieDescription = intent.getStringExtra(NowPlayingMoviesRecyclerViewAdapter.MOVIE_DESCRIPTION_KEY)

        val movieTitleTextView = findViewById<TextView>(R.id.movie_titleTV)
        movieTitleTextView.text = movieTitle

        // Load movie poster using Glide
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500$moviePoster")
            .into(moviePosterImageView)

        // Set budget, release date, tagline, and description to respective TextViews
        budget?.let { budgetTextView.text = "Budget: $it" }
        releaseDate?.let { releaseDateTextView.text = "Release Date: $it" }
        tagline?.let { taglineTextView.text = "Tagline: $it" }
        movieDescription?.let { descriptionTextView.text = "Description: $it" }

        Log.d("MovieDetailActivity", "Title: $movieTitle")
        Log.d("MovieDetailActivity", "Budget: $budget")
        Log.d("MovieDetailActivity", "Release Date: $releaseDate")
        Log.d("MovieDetailActivity", "Tagline: $tagline")
        Log.d("MovieDetailActivity", "Description: $movieDescription")
    }
}