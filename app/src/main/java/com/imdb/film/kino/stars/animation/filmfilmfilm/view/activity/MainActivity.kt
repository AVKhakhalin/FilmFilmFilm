package com.imdb.film.kino.stars.animation.filmfilmfilm.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.imdb.film.kino.stars.animation.filmfilmfilm.R
import com.imdb.film.kino.stars.animation.filmfilmfilm.databinding.ActivityMainBinding
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.base.BaseActivity
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.AppState
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.MAIN_ACTIVITY_SCOPE
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent.getKoin

class MainActivity: BaseActivity<AppState, MainInteractor>() {
    /** Задание исходных данных */ //region
    // Binding
    private lateinit var binding: ActivityMainBinding
    // ViewModel
    override lateinit var model: MainViewModel
    // MainActivityScope
    private val mainActivityScope: Scope = getKoin().getOrCreateScope(
        MAIN_ACTIVITY_SCOPE, named(MAIN_ACTIVITY_SCOPE))
    //endregion

    override fun onDestroy() {
        // Удаление скоупа для данной активити
        mainActivityScope.close()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Инициализация ViewModel
        initViewModel()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setContentView(R.layout.fragment_result_pages)
//        setContentView(R.layout.fragment_result_film)
    }

    // Инициализация ViewModel
    private fun initViewModel() {
        // Начальная установка ViewModel
        val viewModel: MainViewModel by mainActivityScope.inject()
        model = viewModel
        // Подписка на ViewModel
        model.subscribe().observe(this@MainActivity, Observer<AppState> { renderData(it) })
    }

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                Toast.makeText(this, "${appState.data}", Toast.LENGTH_SHORT).show()
            }
            is AppState.Loading -> {
                Toast.makeText(this, "Загрузка", Toast.LENGTH_SHORT).show()
            }
            is AppState.Error -> {
                Toast.makeText(this, appState.error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}