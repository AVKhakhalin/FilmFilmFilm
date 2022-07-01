package com.imdb.film.kino.stars.animation.filmfilmfilm.repository.datasource

import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.DataModel
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.api.ApiService
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.api.BaseInterceptor
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.ADVANCED_SEARCH_URL_LOCATIONS
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImplementation: DataSource<List<DataModel>> {

    override suspend fun getData(title: String, titleType: String, genres: String): List<DataModel> {
        return getService(BaseInterceptor.interceptor).searchAsync(title, titleType, genres).await()
    }

    private fun getService(interceptor: Interceptor): ApiService {
        return createRetrofit(interceptor).create(ApiService::class.java)
    }

    private fun createRetrofit(interceptor: Interceptor): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ADVANCED_SEARCH_URL_LOCATIONS)
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