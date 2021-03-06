package com.imdb.film.kino.stars.animation.filmfilmfilm.repository.datasource

import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.DataModelGeneralFilmInfo
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.ResultFilmInfo
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.api.ApiService
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.api.BaseInterceptor
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.ADVANCED_SEARCH_URL_LOCATION
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.MAX_COUNT_SEARCHED_FILMS
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.RESULT_FILM_SEARCH_URL_LOCATION
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.TOP_SEARCH_URL_LOCATION
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImplementation: DataSource<DataModelGeneralFilmInfo, ResultFilmInfo> {

    override suspend fun getResultFilmData(filmId: String): ResultFilmInfo {
        return getResultFilmService(BaseInterceptor.interceptor).
            resultFilmSearchAsync(filmId).await()
    }

    override suspend fun getData(title: String, titleType: String, genres: String):
            DataModelGeneralFilmInfo {
        return if (title.isNotEmpty()) getAdvancedService(BaseInterceptor.interceptor).
            advancedSearchAsync(title, titleType, genres, MAX_COUNT_SEARCHED_FILMS).await()
        else getTopService(BaseInterceptor.interceptor).topSearchAsync().await()
    }

    private fun getResultFilmService(interceptor: Interceptor): ApiService {
        return createRetrofit(interceptor, RESULT_FILM_SEARCH_URL_LOCATION).
            create(ApiService::class.java)
    }

    private fun getTopService(interceptor: Interceptor): ApiService {
        return createRetrofit(interceptor, TOP_SEARCH_URL_LOCATION).create(ApiService::class.java)
    }

    private fun getAdvancedService(interceptor: Interceptor): ApiService {
        return createRetrofit(interceptor, ADVANCED_SEARCH_URL_LOCATION).
            create(ApiService::class.java)
    }

    private fun createRetrofit(interceptor: Interceptor, baseUrlLink: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrlLink)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(createOkHttpClient(interceptor))
            .build()
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        return httpClient.build()
    }
}