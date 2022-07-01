package com.imdb.film.kino.stars.animation.filmfilmfilm.di

import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.DataModel
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.Repository
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.RepositoryImplementation
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.datasource.RetrofitImplementation
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.NAME_REMOTE
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.imageloader.GlideImageLoaderImpl
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.network.NetworkStatus
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.resources.ResourcesProviderImpl
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.themecolors.ThemeColorsImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    // Удалённый сервер переводчика (API)
    single<Repository<List<DataModel>>>(named(NAME_REMOTE)) {
        RepositoryImplementation(RetrofitImplementation())
    }

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