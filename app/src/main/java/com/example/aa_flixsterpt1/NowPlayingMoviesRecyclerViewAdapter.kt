package com.example.aa_flixsterpt1

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_now_playing_movies, parent, false)
        return MovieViewHolder(view)
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
            holder.mItem?.let { book ->
                mListener?.onItemClick(book)
            }
        }
    }
    /**
     * Remember: RecyclerView adapters require a getItemCount() method.
     */
    override fun getItemCount(): Int {
        return movies.size
    }
}