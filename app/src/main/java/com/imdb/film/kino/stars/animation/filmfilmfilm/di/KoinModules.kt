package com.imdb.film.kino.stars.animation.filmfilmfilm.di

import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.DataModelGeneralFilmInfo
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.Repository
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.RepositoryImplementation
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.datasource.RetrofitImplementation
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.settings.Settings
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.MAIN_ACTIVITY_SCOPE
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.NAME_REMOTE
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.imageloader.GlideImageLoaderImpl
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.network.NetworkStatus
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.resources.ResourcesProviderImpl
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.themecolors.ThemeColorsImpl
import com.imdb.film.kino.stars.animation.filmfilmfilm.view.activity.MainInteractor
import com.imdb.film.kino.stars.animation.filmfilmfilm.view.activity.MainViewModel
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

val mainScreen = module {
    scope(named(MAIN_ACTIVITY_SCOPE)) {
        scoped {
            MainInteractor(
                get(named(NAME_REMOTE)),
                ResourcesProviderImpl(get()),
                NetworkStatus(get())
            )
        }
        viewModel {
            MainViewModel(getScope(MAIN_ACTIVITY_SCOPE).get(), get())
        }
    }
}