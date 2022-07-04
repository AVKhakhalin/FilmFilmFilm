package com.imdb.film.kino.stars.animation.filmfilmfilm.navigator

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.imdb.film.kino.stars.animation.filmfilmfilm.view.fragments.requestinput.RequestInputFragment
import com.imdb.film.kino.stars.animation.filmfilmfilm.view.fragments.resultpages.ResultPagesFragment

class AppScreensImpl: AppScreens {
    // Окно с запросом фильмов
    override fun requestInputScreen() = FragmentScreen {
        RequestInputFragment.newInstance()
    }

    // Окно с результатами запроса фильмов
    override fun resultPagesScreen() = FragmentScreen {
        ResultPagesFragment.newInstance()
    }
//
//    // Окно с детальной информацией по выбранному фильму
//    override fun resultFilmScreen() = FragmentScreen {
//        TODO("Not yet implemented")
//    }
}