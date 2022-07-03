package com.imdb.film.kino.stars.animation.filmfilmfilm.model.base

import androidx.lifecycle.MutableLiveData
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.settings.Settings
import androidx.lifecycle.ViewModel
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.AppState
import kotlinx.coroutines.*

abstract class BaseViewModel<T: AppState>(
    protected open val _mutableLiveData: MutableLiveData<T> = MutableLiveData(),
    protected open val _mutableLiveDataSettings: MutableLiveData<Settings> = MutableLiveData()
): ViewModel() {

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    protected fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    abstract fun getData(filmTitle: String, filmTitleType: String, filmGenre: String)

    abstract fun handleError(error: Throwable)
}