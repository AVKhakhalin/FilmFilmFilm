package com.imdb.film.kino.stars.animation.filmfilmfilm.repository

import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.DataModelGeneralFilmInfo
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.datasource.DataSource

class RepositoryImplementation(
    private val dataSource: DataSource<DataModelGeneralFilmInfo>
): Repository<DataModelGeneralFilmInfo> {

    override suspend fun getData(filmTitle: String, filmTitleType: String, filmGenre: String):
            DataModelGeneralFilmInfo {
        return dataSource.getData(filmTitle, filmTitleType, filmGenre)
    }
}