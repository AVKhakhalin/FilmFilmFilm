package com.imdb.film.kino.stars.animation.filmfilmfilm.repository

import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.DataModel
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.datasource.DataSource

class RepositoryImplementation(
    private val dataSource: DataSource<List<DataModel>>
): Repository<List<DataModel>> {

    override suspend fun getData(title: String, titleType: String, genres: String): List<DataModel> {
        return dataSource.getData(title, titleType, genres)
    }
}