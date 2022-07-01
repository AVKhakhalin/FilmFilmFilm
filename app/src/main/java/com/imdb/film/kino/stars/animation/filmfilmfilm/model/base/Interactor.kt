package com.imdb.film.kino.stars.animation.filmfilmfilm.model.base

interface Interactor<T> {

    suspend fun getData(title: String, titleType: String, genres: String): T
}