package com.example.aa_flixsterpt1

import com.google.gson.annotations.SerializedName

data class NowPlayingMovies(
    @SerializedName("id")
    val id: Int,

    @SerializedName("original_title")
    val movie_title: String?,

    @SerializedName("overview")
    val movie_description: String?,

    @SerializedName("poster_path")
    val movie_poster: String?,

    @SerializedName("budget")
    val budget: Int? = null, // Use Int for budget as it's an integer value in the JSON

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("tagline")
    val tagline: String? = null
)