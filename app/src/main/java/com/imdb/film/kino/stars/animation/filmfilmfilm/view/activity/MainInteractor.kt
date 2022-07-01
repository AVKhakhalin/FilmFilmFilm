package com.imdb.film.kino.stars.animation.filmfilmfilm.view.activity

import android.widget.Toast
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.base.Interactor
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.AppState
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.DataModel
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.Repository
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.network.NetworkStatus
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.resources.ResourcesProvider

class MainInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val resourcesProviderImpl: ResourcesProvider,
    private val networkStatus: NetworkStatus
): Interactor<AppState> {
    override suspend fun getData(title: String, titleType: String, genres: String): AppState {
        val appState: AppState = if (networkStatus.isOnline()) {
            AppState.Success(remoteRepository.getData(title, titleType, genres))
        } else {
            Toast.makeText(resourcesProviderImpl.getContext(), "Для получения информации о фильмах, пожалуйста, установите связь с Интернетом", Toast.LENGTH_SHORT).show()
            AppState.Error(Throwable("Нужно установить связь с Интернетом"))
        }
        return appState
    }
}