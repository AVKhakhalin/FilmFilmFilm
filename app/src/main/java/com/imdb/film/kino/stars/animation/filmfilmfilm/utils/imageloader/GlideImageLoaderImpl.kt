package com.imdb.film.kino.stars.animation.filmfilmfilm.utils.imageloader

import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoaderImpl: ImageLoader<ImageView> {

    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
            .load(url)
            .into(container)
    }
}