package com.imdb.film.kino.stars.animation.filmfilmfilm.model.data

sealed class AppState {
    data class Success(val data: DataModelGeneralFilmInfo?): AppState()
    data class SuccessGeneralFilmInfo(val generalFilmInfoList: List<GeneralFilmInfo>?): AppState()
    data class Error(val error: Throwable): AppState()
    data class Loading(val progress: Int?): AppState()
}