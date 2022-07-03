package com.imdb.film.kino.stars.animation.filmfilmfilm.model.data

import com.google.gson.annotations.SerializedName

class DataModelGeneralFilmInfo(
    @field:SerializedName("queryString") val queryString: String?,
    @field:SerializedName("results", alternate = ["items"]) val advancedSearchResult:
        List<GeneralFilmInfo>?
)