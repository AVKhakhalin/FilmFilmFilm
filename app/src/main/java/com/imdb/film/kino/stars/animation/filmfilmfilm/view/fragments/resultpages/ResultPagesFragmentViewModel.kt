package com.imdb.film.kino.stars.animation.filmfilmfilm.view.fragments.resultpages

import androidx.lifecycle.LiveData
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.base.BaseViewModel
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.AppState
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.settings.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultPagesFragmentViewModel(
    private val interactor: ResultPagesFragmentInteractor,
    private val settings: Settings
): BaseViewModel<AppState>() {
    /** Задание исходных данных */ //region
    // Информация с результатом запроса
    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData
    //endregion

    override fun getData(filmTitle: String, filmTitleType: String, filmGenre: String) {
        // Выборка нужных данных из полученных
        // Значения полей filmTitle, filmTitleType, filmGenre игнорируются
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            startInteractor(filmTitle, filmTitleType, filmGenre)
        }
    }

    private suspend fun startInteractor(
        filmTitle: String, filmTitleType: String, filmGenre: String) =
        withContext(Dispatchers.IO) {
            _mutableLiveData.postValue(interactor.getData(filmTitle, filmTitleType, filmGenre))
        }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }
}