package com.example.aa_flixsterpt1

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aa_flixsterpt1.R.id

/**
 * [RecyclerView.Adapter] that can display a [NowPlayingMovie] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class NowPlayingMoviesRecyclerViewAdapter(
    private val movies: List<NowPlayingMovies>,
    private val mListener: OnListFragmentInteractionListener?
)
    : RecyclerView.Adapter<NowPlayingMoviesRecyclerViewAdapter.MovieViewHolder>()
{
    companion object {
        const val MOVIE_TITLE_KEY = "original_title"
        const val MOVIE_POSTER_KEY = "poster_path"
        const val BUDGET_KEY = "budget"
        const val RELEASE_DATE_KEY = "release_date"
        const val TAGLINE_KEY = "tagline"
        const val MOVIE_DESCRIPTION_KEY = "overview"
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_now_playing_movies, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    /**
     * This inner class lets us refer to all the different View elements
     * (Yes, the same ones as in the XML layout files!)
     */
    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: NowPlayingMovies? = null
        val mMovieTitle: TextView = mView.findViewById<View>(id.movie_title) as TextView
        val mMovieDescription: TextView = mView.findViewById<View>(id.movie_description) as TextView
        val mMoviePoster: ImageView = mView.findViewById<View>(id.movie_poster) as ImageView
    }

    /**
     * This lets us "bind" each Views in the ViewHolder to its' actual data!
     */
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.mItem = movie
        holder.mMovieTitle.text = movie.movie_title
        holder.mMovieDescription.text = movie.movie_description

        val imgURL = "https://image.tmdb.org/t/p/w500${movie.movie_poster}"
        Glide.with(holder.mView)
            .load(imgURL)
            .centerInside()
            .into(holder.mMoviePoster)

        holder.mView.setOnClickListener {
            val intent = Intent(holder.mView.context, MovieDetailActivity::class.java)
            intent.putExtra(NowPlayingMoviesRecyclerViewAdapter.MOVIE_TITLE_KEY, movie.movie_title)
            intent.putExtra(NowPlayingMoviesRecyclerViewAdapter.MOVIE_POSTER_KEY, movie.movie_poster)
            intent.putExtra(NowPlayingMoviesRecyclerViewAdapter.BUDGET_KEY, movie.budget)
            intent.putExtra(NowPlayingMoviesRecyclerViewAdapter.RELEASE_DATE_KEY, movie.releaseDate)
            intent.putExtra(NowPlayingMoviesRecyclerViewAdapter.TAGLINE_KEY, movie.tagline)
            intent.putExtra(NowPlayingMoviesRecyclerViewAdapter.MOVIE_DESCRIPTION_KEY, movie.movie_description)
            holder.mView.context.startActivity(intent)
        }
    }
}