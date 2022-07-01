package com.imdb.film.kino.stars.animation.filmfilmfilm.repository.datasource

interface DataSource<T> {

    suspend fun getData(title: String, titleType: String, genres: String): T
}