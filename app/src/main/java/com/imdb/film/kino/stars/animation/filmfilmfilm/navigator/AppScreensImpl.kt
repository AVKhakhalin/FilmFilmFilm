package com.imdb.film.kino.stars.animation.filmfilmfilm.navigator

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.imdb.film.kino.stars.animation.filmfilmfilm.view.fragments.requestinput.RequestInputFragment

class AppScreensImpl: AppScreens {
    // Окно с запросом фильмов
    override fun requestInputScreen() = FragmentScreen {
        RequestInputFragment.newInstance()
    }

//    // Окно с результатами запроса фильмов
//    override fun resultPagesScreen() = FragmentScreen {
//        TODO("Not yet implemented")
//    }
//
//    // Окно с детальной информацией по выбранному фильму
//    override fun resultFilmScreen() = FragmentScreen {
//        TODO("Not yet implemented")
//    }
}