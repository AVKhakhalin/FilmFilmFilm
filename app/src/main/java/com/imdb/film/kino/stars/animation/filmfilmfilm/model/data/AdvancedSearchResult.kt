package com.imdb.film.kino.stars.animation.filmfilmfilm.model.data

import com.google.gson.annotations.SerializedName

class AdvancedSearchResult(
    @field:SerializedName("id") val filmId: String?,
    @field:SerializedName("image") val filmImageLink: String?,
    @field:SerializedName("title") val filmTitle: String?,
    @field:SerializedName("runtimeStr") val filmRunTime: String?,
    @field:SerializedName("genres") val filmGenres: String?,
    @field:SerializedName("imDbRating") val filmRating: String?,
    @field:SerializedName("plot") val filmOverview: String?,
)