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
