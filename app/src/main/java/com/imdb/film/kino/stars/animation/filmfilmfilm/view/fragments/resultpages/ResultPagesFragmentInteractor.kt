package com.imdb.film.kino.stars.animation.filmfilmfilm.view.fragments.resultpages

import android.widget.Toast
import com.imdb.film.kino.stars.animation.filmfilmfilm.R
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.base.Interactor
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.AppState
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.DataModelGeneralFilmInfo
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.GeneralFilmInfo
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.Repository
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.settings.Settings
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.MAX_NUMBER_FILM_RESULTS_ON_SCREEN
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.getStartElementOnPage
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.network.NetworkStatus
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.resources.ResourcesProvider
import org.koin.java.KoinJavaComponent.getKoin

class ResultPagesFragmentInteractor(
    private val remoteRepository: Repository<DataModelGeneralFilmInfo>,
    private val resourcesProviderImpl: ResourcesProvider,
    private val networkStatus: NetworkStatus
): Interactor<AppState> {
    /** Исходные данные */ //region
    private val settings: Settings = getKoin().get()
    //endregion
    override suspend fun getData(filmTitle: String, filmTitleType: String, filmGenre: String):
            AppState {
        val appState: AppState = if (settings.advancedSearchResult.size > 0) {
            val newGeneralFilmInfoList: MutableList<GeneralFilmInfo> = mutableListOf()
            repeat(MAX_NUMBER_FILM_RESULTS_ON_SCREEN) {
                if (settings.advancedSearchResult.size >
                    settings.pagingNumber.getStartElementOnPage() + it)
                    newGeneralFilmInfoList.add(settings.advancedSearchResult[
                            settings.pagingNumber.getStartElementOnPage() + it])
            }
            AppState.SuccessGeneralFilmInfo(newGeneralFilmInfoList)
        } else {
            Toast.makeText(resourcesProviderImpl.getContext(),
                resourcesProviderImpl.getString(R.string.no_founded_films),
                Toast.LENGTH_SHORT).show()
            AppState.Error(Throwable(
                resourcesProviderImpl.getString(R.string.error_no_founded_films)))
        }
        return appState
    }
}