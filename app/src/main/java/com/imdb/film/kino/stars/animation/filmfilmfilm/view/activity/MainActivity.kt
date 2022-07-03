package com.imdb.film.kino.stars.animation.filmfilmfilm.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.imdb.film.kino.stars.animation.filmfilmfilm.R
import com.imdb.film.kino.stars.animation.filmfilmfilm.databinding.ActivityMainBinding
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.base.BaseActivity
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.AppState
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.settings.Settings
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.MAIN_ACTIVITY_SCOPE
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.convertToInquiry
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent
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
    // Поля для ввода запроса с информацией о фильмах
    private lateinit var titleSearchField: EditText
    private lateinit var titleTypeSearchField: Spinner
    private lateinit var genresSearchField: Spinner
    // Кнопка для отправки запроса с информацией о фильмах
    private lateinit var searchButton: Button
    // Класс для сохранения запроса
    private val settings: Settings = getKoin().get()
    //endregion

    override fun onDestroy() {
        // Удаление скоупа для данной активити
        mainActivityScope.close()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Подключение Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(R.layout.fragment_result_pages)
//        setContentView(R.layout.fragment_result_film)
        setContentView(binding.root)

        // Инициализация ViewModel
        initViewModel()
        // Инициализация полей ввода исходной информации и кнопки для отправки запроса
        initFieldsAndSearchButton()
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
                Toast.makeText(this, "Success:\n" +
                    "Общее количество найденных фильмов: ${appState.data?.advancedSearchResult?.size}\n" +
                    "${appState.data?.advancedSearchResult?.get(0)?.filmId}\n" +
                    "${appState.data?.advancedSearchResult?.get(0)?.filmTitle}\n" +
                    "${appState.data?.advancedSearchResult?.get(0)?.filmImageLink}\n" +
                    "${appState.data?.advancedSearchResult?.get(0)?.filmRating}",
                    Toast.LENGTH_SHORT).show()
            }
            is AppState.Loading -> {
                Toast.makeText(this, "Загрузка", Toast.LENGTH_SHORT).show()
            }
            is AppState.Error -> {
                Toast.makeText(this, appState.error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Инициализация полей ввода исходной информации и кнопки для отправки запроса
    private fun initFieldsAndSearchButton() {
        titleSearchField = binding.filmTitleInputField
        titleSearchField.doOnTextChanged { _, _, _, _ ->
            settings.filmTitle = titleSearchField.text.toString()
        }
        titleTypeSearchField = binding.filmTitleTypesList.also { it.setSelection(0)}

        titleTypeSearchField.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            var selectCounter: Int = 0
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long,
            ) {
                // Действие, в случае выбора элемента списка
                if ((selectCounter++ > 0) && (position > 0)) {
                    settings.filmTitleType =
                        titleTypeSearchField.selectedItem.toString().convertToInquiry()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        genresSearchField = binding.filmGenresList.also { it.setSelection(0)}
        genresSearchField.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            var selectCounter: Int = 0
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long,
            ) {
                // Действие, в случае выбора элемента списка
                if ((selectCounter++ > 0) && (position > 0)) {
                    settings.filmGenre =
                        genresSearchField.selectedItem.toString().convertToInquiry()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        searchButton = binding.runSearchButton
        searchButton.setOnClickListener {
            // Запуск поиска
            model.getData(
                titleSearchField.text.toString(),
                titleTypeSearchField.selectedItem.toString().convertToInquiry(),
                genresSearchField.selectedItem.toString().convertToInquiry()
            )
        }
    }

}