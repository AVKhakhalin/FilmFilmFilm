package com.imdb.film.kino.stars.animation.filmfilmfilm.model.base

import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import com.imdb.film.kino.stars.animation.filmfilmfilm.navigator.AppScreens
import com.imdb.film.kino.stars.animation.filmfilmfilm.navigator.AppScreensImpl
import org.koin.java.KoinJavaComponent.getKoin

abstract class BaseViewModelForNavigation: ViewModel() {
    /** Исходные данные */ //region
    // Навигация
    val screens: AppScreens = getKoin().get()
    val router: Router = getKoin().get()
    //endregion
}