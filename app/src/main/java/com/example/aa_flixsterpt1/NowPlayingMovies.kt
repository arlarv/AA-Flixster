package com.example.aa_flixsterpt1
import com.google.gson.annotations.SerializedName

class NowPlayingMovies {

    @SerializedName("original_title")
    var movie_title: String? = null

    @SerializedName("overview")
    var movie_description: String? = null

    @SerializedName("poster_path")
    var movie_poster: String? = null



}
