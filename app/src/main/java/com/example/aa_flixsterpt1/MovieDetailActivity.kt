package com.example.aa_flixsterpt1

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.NumberFormat
import java.util.*

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        // Set the views
        val moviePosterImageView = findViewById<ImageView>(R.id.imageView)
        val budgetTextView = findViewById<TextView>(R.id.movie_budgetTV)
        val releaseDateTextView = findViewById<TextView>(R.id.movie_release_dateTV)
        val taglineTextView = findViewById<TextView>(R.id.movie_taglineTV)
        val movieTitleTextView = findViewById<TextView>(R.id.movie_titleTV)
        val movieDescriptionTextView = findViewById<TextView>(R.id.movie_descriptionTV)
        // Get movie ID from the intent
        val movieId = intent.getIntExtra(NowPlayingMoviesRecyclerViewAdapter.MOVIE_ID_KEY, 0)

        // Construct the API endpoint URL with the movie ID and your API key
        val apiKey = "5e0044139a88016b00ac28b744763d2b"
        val apiUrl = "https://api.themoviedb.org/3/movie/$movieId?api_key=$apiKey"

        // Create OkHttp client and request object
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(apiUrl)
            .build()

        // Make the API call using OkHttp
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle API call failure
                Log.e("MovieDetailActivity", "API call failed: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                // Handle API call success
                val jsonData = response.body?.string()
                val jsonObject = JSONObject(jsonData)

                // Parse the JSON response to get movie details
                val budget = jsonObject.getInt("budget")
                val tagline = jsonObject.getString("tagline")
                val title = jsonObject.getString("original_title")
                val posterPath = jsonObject.getString("poster_path")
                val releaseDate = jsonObject.getString("release_date")
                val description = jsonObject.getString("overview")

                val formattedBudget = formatBudgetWithCommas(budget)

                // Update UI on the main thread
                runOnUiThread {
                    // Set all the details to their respective TextViews and ImageView
                    budgetTextView.text = "Budget: $$formattedBudget"
                    taglineTextView.text = "$tagline"
                    movieTitleTextView.text = title
                    releaseDateTextView.text = "Release Date: $releaseDate"
                    movieDescriptionTextView.text = "$description"

                    // Load movie poster using Glide
                    Glide.with(this@MovieDetailActivity)
                        .load("https://image.tmdb.org/t/p/w500$posterPath")
                        .into(moviePosterImageView)
                }
            }
            private fun formatBudgetWithCommas(budget: Int): String {
                val format = NumberFormat.getNumberInstance(Locale.US)
                return format.format(budget)
            }
        })
    }
}
