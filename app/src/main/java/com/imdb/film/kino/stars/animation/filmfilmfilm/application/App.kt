package com.imdb.film.kino.stars.animation.filmfilmfilm.application

import android.app.Application
import com.imdb.film.kino.stars.animation.filmfilmfilm.di.application
import com.imdb.film.kino.stars.animation.filmfilmfilm.di.mainScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    /** Задание переменных */ //region
    companion object {
        lateinit var instance: App
    }
    // endregion

    override fun onCreate() {
        super.onCreate()
        // Инициализация класса App
        instance = this

        // Koin
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, mainScreen))
        }
    }
}