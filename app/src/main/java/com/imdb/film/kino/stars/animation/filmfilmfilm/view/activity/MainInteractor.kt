package com.imdb.film.kino.stars.animation.filmfilmfilm.view.activity

import android.widget.Toast
import com.imdb.film.kino.stars.animation.filmfilmfilm.R
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.base.Interactor
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.AppState
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.DataModelGeneralFilmInfo
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.Repository
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.network.NetworkStatus
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.resources.ResourcesProvider

class MainInteractor(
    private val remoteRepository: Repository<DataModelGeneralFilmInfo>,
    private val resourcesProviderImpl: ResourcesProvider,
    private val networkStatus: NetworkStatus
): Interactor<AppState> {
    override suspend fun getData(filmTitle: String, filmTitleType: String, filmGenre: String):
            AppState {
        val appState: AppState = if (networkStatus.isOnline()) {
            AppState.Success(remoteRepository.getData(filmTitle, filmTitleType, filmGenre))
        } else {
            Toast.makeText(resourcesProviderImpl.getContext(),
                resourcesProviderImpl.getString(R.string.help_needs_internet_connection),
                Toast.LENGTH_SHORT).show()
            AppState.Error(Throwable(
                resourcesProviderImpl.getString(R.string.error_needs_internet_connection)))
        }
        return appState
    }
}