package com.imdb.film.kino.stars.animation.filmfilmfilm.view.fragments.requestinput

import android.widget.Toast
import com.imdb.film.kino.stars.animation.filmfilmfilm.R
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.base.Interactor
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.AppState
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.DataModelGeneralFilmInfo
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.ResultFilmInfo
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.Repository
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.settings.Settings
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.network.NetworkStatus
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.resources.ResourcesProvider
import org.koin.java.KoinJavaComponent.getKoin

class RequestInputFragmentInteractor(
    private val remoteRepository: Repository<DataModelGeneralFilmInfo, ResultFilmInfo>,
    private val resourcesProviderImpl: ResourcesProvider,
    private val networkStatus: NetworkStatus
): Interactor<AppState> {
    /** Исходные данные */ //region
    private val settings: Settings = getKoin().get()
    //endregion

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
        // Сохранение данных в класс Settings
        (appState as AppState.Success).data?.let {
            it.advancedSearchResult?.let { generalFilmInfoList ->
                settings.pagingNumber = 0
                settings.advancedSearchResult.clear()
                generalFilmInfoList.forEach { generalFilmInfo ->
                    settings.advancedSearchResult.add(generalFilmInfo)
                }
            }
        }

        return appState
    }
}