package com.imdb.film.kino.stars.animation.filmfilmfilm.view.activity

import androidx.lifecycle.LiveData
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.base.BaseViewModel
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.AppState
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.settings.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val interactor: MainInteractor,
    private val settings: Settings
): BaseViewModel<AppState>() {
    /** Задание исходных данных */ //region
    // Информация с результатом запроса
    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData
    //endregion

    override fun getData(title: String, titleType: String, genres: String) {
        // Выполнение поиска
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(title, titleType, genres) }
    }

    private suspend fun startInteractor(title: String, titleType: String, genres: String) =
        withContext(Dispatchers.IO) {
        _mutableLiveData.postValue(interactor.getData(title, titleType, genres))
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }
}