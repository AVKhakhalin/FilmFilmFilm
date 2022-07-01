package com.imdb.film.kino.stars.animation.filmfilmfilm.model.base

import androidx.fragment.app.FragmentActivity
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.AppState

abstract class BaseActivity<T: AppState, I: Interactor<T>>: FragmentActivity() {

    abstract val model: BaseViewModel<T>

    abstract fun renderData(dataModel: T)
}