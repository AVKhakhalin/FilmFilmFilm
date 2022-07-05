package com.imdb.film.kino.stars.animation.filmfilmfilm.view.fragments.resultfilm

import android.widget.Toast
import com.imdb.film.kino.stars.animation.filmfilmfilm.R
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.base.Interactor
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.AppState
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.DataModelGeneralFilmInfo
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.GeneralFilmInfo
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.ResultFilmInfo
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.Repository
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.settings.Settings
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.MAX_NUMBER_FILM_RESULTS_ON_SCREEN
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.getStartElementOnPage
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.network.NetworkStatus
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.resources.ResourcesProvider
import org.koin.java.KoinJavaComponent

class ResultFilmFragmentInteractor(
    private val remoteRepository: Repository<DataModelGeneralFilmInfo, ResultFilmInfo>,
    private val resourcesProviderImpl: ResourcesProvider,
    private val networkStatus: NetworkStatus
): Interactor<AppState> {
    /** Исходные данные */ //region
    private val settings: Settings = KoinJavaComponent.getKoin().get()
    //endregion

    override suspend fun getData(filmTitle: String, filmTitleType: String, filmGenre: String):
            AppState {
        val appState: AppState = if (networkStatus.isOnline()) {
            AppState.SuccessResulFilmInfo(
                remoteRepository.getResultFilmData(settings.idChoosedFilm))
        } else {
            Toast.makeText(resourcesProviderImpl.getContext(),
                resourcesProviderImpl.getString(R.string.help_needs_internet_connection),
                Toast.LENGTH_SHORT).show()
            AppState.Error(Throwable(
                resourcesProviderImpl.getString(R.string.error_needs_internet_connection)))
        }
        // Сохранение данных в класс Settings
        (appState as AppState.SuccessResulFilmInfo).resultFilmInfo?.let { resultFilmInfo->
            settings.resultFilmInfo = ResultFilmInfo(
                resultFilmInfo.filmId,
                resultFilmInfo.filmImageLink,
                resultFilmInfo.filmTitle,
                resultFilmInfo.releaseDate,
                resultFilmInfo.filmRunTime,
                resultFilmInfo.filmGenres,
                resultFilmInfo.filmRating,
                resultFilmInfo.filmOverview,
                resultFilmInfo.actorList
            )
        }

        return appState
    }
}