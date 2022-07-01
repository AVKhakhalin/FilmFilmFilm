package com.imdb.film.kino.stars.animation.filmfilmfilm.utils.themecolors

import android.content.res.Resources
import android.util.TypedValue
import com.imdb.film.kino.stars.animation.filmfilmfilm.R

class ThemeColorsImpl: ThemeColors {
    //region ЗАДАНИЕ ПЕРЕМЕННЫХ
    private val colorSecondaryTypedValue: TypedValue = TypedValue()
    private val colorTypedValue: TypedValue = TypedValue()
    private val colorSecondaryVariantTypedValue: TypedValue = TypedValue()
    private val colorPrimaryVariantTypedValue: TypedValue = TypedValue()
    private val colorPrimaryTypedValue: TypedValue = TypedValue()
    private val colorSurfaceTypedValue: TypedValue = TypedValue()
    //endregion

    override fun initiateColors(resourcesTheme: Resources.Theme) {
        resourcesTheme.resolveAttribute(
            com.google.android.material.R.attr.colorSecondary,
            colorSecondaryTypedValue, true
        )
        resourcesTheme.resolveAttribute(
            com.google.android.material.R.attr.color, colorTypedValue, true)
        resourcesTheme.resolveAttribute(
            com.google.android.material.R.attr.colorSecondaryVariant,
            colorSecondaryVariantTypedValue, true
        )
        resourcesTheme.resolveAttribute(
            com.google.android.material.R.attr.colorPrimaryVariant,
            colorPrimaryVariantTypedValue, true
        )
        resourcesTheme.resolveAttribute(
            com.google.android.material.R.attr.colorPrimary, colorPrimaryTypedValue, true
        )
        resourcesTheme.resolveAttribute(
            com.google.android.material.R.attr.colorSurface, colorSurfaceTypedValue, true
        )
    }

    //region МЕТОДЫ ПОЛУЧЕНИЯ ЦВЕТОВ ИЗ АТТРИБУТОВ ТЕМЫ
    fun getColorSecondaryTypedValue(): Int {
        return colorSecondaryTypedValue.data
    }
    fun getColorTypedValue(): Int {
        return colorTypedValue.data
    }
    fun getSecondaryVariantTypedValue(): Int {
        return colorSecondaryVariantTypedValue.data
    }
    fun getColorPrimaryVariantTypedValue(): Int {
        return colorPrimaryVariantTypedValue.data
    }
    fun getColorPrimaryTypedValue(): Int {
        return colorPrimaryTypedValue.data
    }
    fun getColorSurfaceTypedValue(): Int {
        return colorSurfaceTypedValue.data
    }
    //endregion
}