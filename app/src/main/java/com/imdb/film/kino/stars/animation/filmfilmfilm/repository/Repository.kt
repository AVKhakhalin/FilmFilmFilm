package com.imdb.film.kino.stars.animation.filmfilmfilm.repository

interface Repository<T, D> {

    suspend fun getData(filmTitle: String, filmTitleType: String, filmGenre: String): T
    suspend fun getResultFilmData(filmId: String): D
}