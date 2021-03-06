package com.imdb.film.kino.stars.animation.filmfilmfilm.repository.settings

import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.GeneralFilmInfo
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.ResultFilmInfo

class Settings {
    /** Исходные данные */ //region
    // Поля поискового запроса
    var filmTitle: String = ""
    var filmTitleType: String = ""
    var filmGenre: String = ""
    // Список найденных фильмов
    var advancedSearchResult: MutableList<GeneralFilmInfo> = mutableListOf()
    // Номер позиции в пагинации по умолчанию
    var pagingNumber: Int = 0
    // Id номер выбранного пользователем фильма
    var idChoosedFilm: String = ""
    // Детальная информация о выбранном фильме
    var resultFilmInfo: ResultFilmInfo = ResultFilmInfo("", "", "",
        "", "", "", "", "", listOf())
    //endregion
}