package com.example.aa_flixsterpt1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import org.json.JSONArray
import org.json.JSONObject

class NowPlayingMoviesFragment : Fragment(), OnListFragmentInteractionListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_now_playing_movies_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        val client = AsyncHttpClient()
        val params = RequestParams()
        params.put("api_key", "5e0044139a88016b00ac28b744763d2b")
        params.put("original_language", "en-US")
        params.put("sort_by", "popularity.desc")
        params.put("primary_release_date.gte", "2023-01-01")
        params.put("with_genres", "27")

        Log.d("NowPlayingMoviesFragment", "Before API call")

        client.get("https://api.themoviedb.org/3/discover/movie", params, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out cz.msebera.android.httpclient.Header>?, responseBody: ByteArray?) {
                progressBar.hide()
                try {
                    val responseString = responseBody?.toString(Charsets.UTF_8)
                    val json = JSONObject(responseString)
                    val resultsArray: JSONArray = json.getJSONArray("results")
                    val models = mutableListOf<NowPlayingMovies>()

                    for (i in 0 until resultsArray.length()) {
                        val movieObject: JSONObject = resultsArray.getJSONObject(i)
                        val movie = NowPlayingMovies()
                        movie.movie_title = movieObject.getString("original_title")
                        movie.movie_description = movieObject.getString("overview")
                        movie.movie_poster = movieObject.getString("poster_path")
                        models.add(movie)
                    }

                    recyclerView.adapter = NowPlayingMoviesRecyclerViewAdapter(models, this@NowPlayingMoviesFragment)
                    Log.d("NowPlayingMoviesFragment", "API call successful!")
                } catch (e: Exception) {
                    Log.e("NowPlayingMoviesFragment", "Error parsing JSON: ${e.message}")
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out cz.msebera.android.httpclient.Header>?, responseBody: ByteArray?, error: Throwable?) {
                progressBar.hide()
                Log.e("NowPlayingMoviesFragment", "API call failed. Status code: $statusCode")
            }
        })
        Log.d("NowPlayingMoviesFragment", "After API call")
    }

    override fun onItemClick(item: NowPlayingMovies) {
        Toast.makeText(context, "Movie Title: ${item.movie_title}", Toast.LENGTH_LONG).show()
    }
}

/*
FOR INTENTS, SHOW THESE DETAILS
RELEASE YEAR
BUDGET
TAGLINE
 */


/* EXAMPLE OF SINGLE MOVIE RESPONSE AND JSON TAGS
{
  "adult": false,
  "backdrop_path": "/mRGmNnh6pBAGGp6fMBMwI8iTBUO.jpg",
  "belongs_to_collection": {
    "id": 968052,
    "name": "The Nun Collection",
    "poster_path": "/d998jmbM0AQ9Ad138Mz0N9CdnOU.jpg",
    "backdrop_path": "/bKpqH9y3SjovMM3VqzezBbJtuf7.jpg"
  },
  "budget": 38500000,
  "genres": [
    {
      "id": 27,
      "name": "Horror"
    },
    {
      "id": 9648,
      "name": "Mystery"
    },
    {
      "id": 53,
      "name": "Thriller"
    }
  ],
  "homepage": "https://www.warnerbros.com/movies/nun2",
  "id": 968051,
  "imdb_id": "tt10160976",
  "original_language": "en",
  "original_title": "The Nun II",
  "overview": "In 1956 France, a priest is violently murdered, and Sister Irene begins to investigate. She once again comes face-to-face with a powerful evil.",
  "popularity": 5953.02,
  "poster_path": "/5gzzkR7y3hnY8AD1wXjCnVlHba5.jpg",
  "production_companies": [
    {
      "id": 12,
      "logo_path": "/mevhneWSqbjU22D1MXNd4H9x0r0.png",
      "name": "New Line Cinema",
      "origin_country": "US"
    },
    {
      "id": 76907,
      "logo_path": "/ygMQtjsKX7BZkCQhQZY82lgnCUO.png",
      "name": "Atomic Monster",
      "origin_country": "US"
    },
    {
      "id": 11565,
      "logo_path": null,
      "name": "The Safran Company",
      "origin_country": "US"
    }
  ],
  "production_countries": [
    {
      "iso_3166_1": "US",
      "name": "United States of America"
    }
  ],
  "release_date": "2023-09-06",
  "revenue": 235010000,
  "runtime": 110,
  "spoken_languages": [
    {
      "english_name": "English",
      "iso_639_1": "en",
      "name": "English"
    },
    {
      "english_name": "French",
      "iso_639_1": "fr",
      "name": "Français"
    }
  ],
  "status": "Released",
  "tagline": "Confess your sins.",
  "title": "The Nun II",
  "video": false,
  "vote_average": 6.99,
  "vote_count": 737
}

 */