package com.imdb.film.kino.stars.animation.filmfilmfilm.repository.api

import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.DataModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("words/search")
    fun searchAsync(@Query("title") title: String,
                    @Query("titleType") titleType: String,
                    @Query("genres") genres: String
    ): Deferred<List<DataModel>>
}