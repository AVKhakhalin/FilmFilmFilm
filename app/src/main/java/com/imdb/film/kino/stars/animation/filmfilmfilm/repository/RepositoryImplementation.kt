package com.imdb.film.kino.stars.animation.filmfilmfilm.repository

import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.DataModelGeneralFilmInfo
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.ResultFilmInfo
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.datasource.DataSource

class RepositoryImplementation(
    private val dataSource: DataSource<DataModelGeneralFilmInfo, ResultFilmInfo>
): Repository<DataModelGeneralFilmInfo, ResultFilmInfo> {

    override suspend fun getData(filmTitle: String, filmTitleType: String, filmGenre: String):
            DataModelGeneralFilmInfo {
        return dataSource.getData(filmTitle, filmTitleType, filmGenre)
    }

    override suspend fun getResultFilmData(filmId: String): ResultFilmInfo {
        return dataSource.getResultFilmData(filmId)
    }
}