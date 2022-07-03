package com.imdb.film.kino.stars.animation.filmfilmfilm.utils

fun String.convertToInquiry(): String {
    return this.lowercase().replace(" ", "_").replace("-", "_")
}