package com.imdb.film.kino.stars.animation.filmfilmfilm.utils.resources

import android.content.Context
import androidx.annotation.StringRes

interface ResourcesProvider {

    fun getString(@StringRes id: Int): String
    fun getContext(): Context
}