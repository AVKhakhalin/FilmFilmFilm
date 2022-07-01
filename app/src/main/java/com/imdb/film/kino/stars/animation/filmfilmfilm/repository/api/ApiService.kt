package com.imdb.film.kino.stars.animation.filmfilmfilm.repository.api

import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.DataModel
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.IMDB_KEY_VALUE
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("$IMDB_KEY_VALUE")
    fun searchAsync(@Query("title") title: String,
                    @Query("title_type") titleType: String,
                    @Query("genres") genres: String
    ): Deferred<List<DataModel>>
}