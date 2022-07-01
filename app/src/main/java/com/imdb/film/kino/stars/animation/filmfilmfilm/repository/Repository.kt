package com.imdb.film.kino.stars.animation.filmfilmfilm.repository

interface Repository<T> {

    suspend fun getData(title: String, titleType: String, genres: String): T
}