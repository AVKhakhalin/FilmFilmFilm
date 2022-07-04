package com.imdb.film.kino.stars.animation.filmfilmfilm.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.DataModelGeneralFilmInfo
import com.imdb.film.kino.stars.animation.filmfilmfilm.navigator.AppScreens
import com.imdb.film.kino.stars.animation.filmfilmfilm.navigator.AppScreensImpl
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.Repository
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.RepositoryImplementation
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.datasource.RetrofitImplementation
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.settings.Settings
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.*
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.imageloader.GlideImageLoaderImpl
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.network.NetworkStatus
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.resources.ResourcesProviderImpl
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.themecolors.ThemeColorsImpl
import com.imdb.film.kino.stars.animation.filmfilmfilm.view.activity.MainViewModel
import com.imdb.film.kino.stars.animation.filmfilmfilm.view.fragments.requestinput.RequestInputFragmentInteractor
import com.imdb.film.kino.stars.animation.filmfilmfilm.view.fragments.requestinput.RequestInputFragmentViewModel
import com.imdb.film.kino.stars.animation.filmfilmfilm.view.fragments.resultpages.ResultPagesFragmentInteractor
import com.imdb.film.kino.stars.animation.filmfilmfilm.view.fragments.resultpages.ResultPagesFragmentViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val application = module {
    // Удалённый сервер (API)
    single<Repository<DataModelGeneralFilmInfo>>(named(NAME_REMOTE)) {
        RepositoryImplementation(RetrofitImplementation())
    }
    // Локальное сохранение данных
    single<Settings> { Settings() }

    // Навигация между окнами
    single<Cicerone<Router>>(named(CICERONE_NAME)) { Cicerone.create() }
    single<NavigatorHolder> {
        get<Cicerone<Router>>(named(CICERONE_NAME)).getNavigatorHolder() }
    single<Router> { get<Cicerone<Router>>(named(CICERONE_NAME)).router }
    single<AppScreens> { AppScreensImpl() }

    // Вспомогательные классы:
    // Определение статуса сети
    single<NetworkStatus> { NetworkStatus(androidContext()) }
    // Получение доступа к ресурсам
    single<ResourcesProviderImpl> { ResourcesProviderImpl(androidContext()) }
    // Получение доступа к цветам темы
    single<ThemeColorsImpl> { ThemeColorsImpl() }
    // Загрузка изображений с помощью библиотеки Glide
    single<GlideImageLoaderImpl> { GlideImageLoaderImpl() }
}

val screens = module {
    // Scope для MainActivity
    scope(named(MAIN_ACTIVITY_SCOPE)) {
        viewModel {
            MainViewModel()
        }
    }

    // Scope для фрагмента с поисковым запросом
    scope(named(REQUEST_INPUT_FRAGMENT_SCOPE)) {
        scoped {
            RequestInputFragmentInteractor(
                get(named(NAME_REMOTE)),
                ResourcesProviderImpl(get()),
                NetworkStatus(get())
            )
        }
        viewModel {
            RequestInputFragmentViewModel(getScope(REQUEST_INPUT_FRAGMENT_SCOPE).get(), get())
        }
    }

    // Scope для фрагмента со списком найденных фильмов для создания пагинации
    scope(named(RESULT_PAGES_FRAGMENT_SCOPE)) {
        scoped {
            ResultPagesFragmentInteractor(
                get(named(NAME_REMOTE)),
                ResourcesProviderImpl(get()),
                NetworkStatus(get())
            )
        }
        viewModel {
            ResultPagesFragmentViewModel(getScope(RESULT_PAGES_FRAGMENT_SCOPE).get(), get())
        }
    }
}