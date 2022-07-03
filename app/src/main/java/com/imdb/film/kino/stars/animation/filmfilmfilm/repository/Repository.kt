package com.imdb.film.kino.stars.animation.filmfilmfilm.repository

interface Repository<T> {

    suspend fun getData(filmTitle: String, filmTitleType: String, filmGenre: String): T
}