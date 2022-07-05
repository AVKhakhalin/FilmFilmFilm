package com.imdb.film.kino.stars.animation.filmfilmfilm.repository.datasource

interface DataSource<T, D> {

    suspend fun getData(title: String, titleType: String, genres: String): T
    suspend fun getResultFilmData(filmId: String): D
}