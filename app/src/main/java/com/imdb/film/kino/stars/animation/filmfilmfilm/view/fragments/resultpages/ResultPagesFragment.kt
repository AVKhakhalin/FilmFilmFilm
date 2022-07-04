package com.imdb.film.kino.stars.animation.filmfilmfilm.view.fragments.resultpages

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.imdb.film.kino.stars.animation.filmfilmfilm.databinding.FragmentResultPagesBinding
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.base.BaseFragment
import com.imdb.film.kino.stars.animation.filmfilmfilm.model.data.AppState
import com.imdb.film.kino.stars.animation.filmfilmfilm.repository.settings.Settings
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.*
import com.imdb.film.kino.stars.animation.filmfilmfilm.utils.imageloader.GlideImageLoaderImpl
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent
import org.koin.java.KoinJavaComponent.getKoin

class ResultPagesFragment:
    BaseFragment<FragmentResultPagesBinding>(FragmentResultPagesBinding::inflate) {
    /** Задание переменных */ //region
    // ViewModel
    private lateinit var viewModel: ResultPagesFragmentViewModel
    // ShowRequestInputFragmentScope
    private lateinit var showResultPagesFragmentScope: Scope
    // Класс для сохранения запроса
    private val settings: Settings = getKoin().get()
    // Навигационные кнопки
    private lateinit var backButton: Button
    // Индикаторы загрузки
    private lateinit var progressBarsList: List<ProgressBar>
    // Контейнеры для картинок
    private lateinit var imageViewList: List<ImageView>
    // Названия найденных фильмов
    private lateinit var filmsTitles: List<TextView>
    // Даты найденных фильмов
    private lateinit var filmsDates: List<TextView>
    // Рейтинги найденных фильмов
    private lateinit var filmsRaitings: List<CircularProgressIndicator>
    private lateinit var filmsRaitingsNumbers: List<TextView>
    // GlideImageLoaderImpl
    private val glideImageLoaderImpl: GlideImageLoaderImpl = getKoin().get()
    // newInstance для данного класса
    companion object {
        fun newInstance(): ResultPagesFragment = ResultPagesFragment()
    }
    //endregion

    /** Работа со Scope */ //region
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Задание Scope для данного фрагмента
        showResultPagesFragmentScope = KoinJavaComponent.getKoin().getOrCreateScope(
            RESULT_PAGES_FRAGMENT_SCOPE, named(RESULT_PAGES_FRAGMENT_SCOPE)
        )
    }
    override fun onDetach() {
        // Удаление скоупа для данного фрагмента
        showResultPagesFragmentScope.close()
        super.onDetach()
    }
    //endregion

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация ViewModel
        initViewModel()
        // Инициализация кнопок
        initButtons()
        // Инициализация индикаторов загрузки
        initProgressBars()
        // Инициализация контейнеров для картинок
        initImageViews()
        // Инициализация названий найденных фильмов
        initFilmsTitles()
        // Инициализация дат найденных фильмов
        initFilmsDates()
        // Инициализация рейтингов найденных фильмов
        initFilmsRaitings()
    }

    // Инициализация ViewModel
    private fun initViewModel() {
        val _viewModel: ResultPagesFragmentViewModel by showResultPagesFragmentScope.inject()
        viewModel = _viewModel
        // Подписка на ViewModel
        viewModel.subscribe().observe(viewLifecycleOwner) { renderData(it) }
        // Загрузка данных
        viewModel.getData("", "", "")
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessGeneralFilmInfo -> {
                progressBarsList.forEach { it.visibility = View.INVISIBLE }
//                Toast.makeText(requireContext(), "${appState.generalFilmInfoList?.size}\n" +
//                        "${appState.generalFilmInfoList?.get(0)?.filmTitle}\n" +
//                        "${appState.generalFilmInfoList?.get(1)?.filmTitle}\n" +
//                        "${appState.generalFilmInfoList?.get(2)?.filmTitle}\n" +
//                        "${appState.generalFilmInfoList?.get(3)?.filmTitle}" +
//                        "", Toast.LENGTH_SHORT).show()

                appState.generalFilmInfoList?.let { generalFilmInfoList ->
                    repeat(MAX_NUMBER_FILM_RESULTS_ON_SCREEN) {
                        if (generalFilmInfoList.size >
                            settings.pagingNumber.getStartElementOnPage() + it) {
                            // Установка картинок на найденные фильмы
                            val imageLink: String = "${generalFilmInfoList[settings.pagingNumber.
                            getStartElementOnPage() + it].filmImageLink}"
                            glideImageLoaderImpl.loadInto(imageLink, imageViewList[it])
                            // Установка названий найденных фильмов
                            filmsTitles[it].text = "${generalFilmInfoList[settings.pagingNumber.
                            getStartElementOnPage() + it].filmTitle}"
                            // Установка дат найденных фильмов
                            filmsDates[it].text = "${generalFilmInfoList[settings.pagingNumber.
                            getStartElementOnPage() + it].filmData}".deleteBrackets()
                            // Установка рейтингов найденных фильмов
                            val raiting: Int = "${generalFilmInfoList[settings.pagingNumber.
                                getStartElementOnPage() + it].filmRating}".convertToProgress()
                            filmsRaitings[it].progress = raiting
                            filmsRaitings[it].setIndicatorColor(raiting.convertToColor())
                            filmsRaitingsNumbers[it].text = "$raiting"
                        }
                    }
                }
            }
            is AppState.Loading -> {
                progressBarsList.forEach { it.visibility = View.VISIBLE }
            }
            is AppState.Error -> {
                Toast.makeText(requireContext(), appState.error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Инициализация кнопок
    private fun initButtons() {
        backButton = binding.backButton
        backButton.setOnClickListener {
            viewModel.router.exit()
        }
    }

    // Инициализация индикаторов загрузки
    private fun initProgressBars() {
        progressBarsList = listOf(
            binding.contentStartTopFilmImageProgressbar,
            binding.contentEndTopFilmImageProgressbar,
            binding.contentStartBottomFilmImageProgressbar,
            binding.contentEndBottomFilmImageProgressbar
        )
        progressBarsList.forEach { it.visibility = View.INVISIBLE }
    }

    // Инициализация контейнеров для картинок
    private fun initImageViews() {
        imageViewList = listOf(
            binding.contentStartTopFilmImage,
            binding.contentEndTopFilmImage,
            binding.contentStartBottomFilmImage,
            binding.contentEndBottomFilmImage
        )
    }

    // Инициализация названий найденных фильмов
    private fun initFilmsTitles() {
        filmsTitles = listOf(
            binding.contentStartTopFilmTitle,
            binding.contentEndTopFilmTitle,
            binding.contentStartBottomFilmTitle,
            binding.contentEndBottomFilmTitle
        )
    }

    // Инициализация дат найденных фильмов
    private fun initFilmsDates() {
        filmsDates = listOf(
            binding.contentStartTopFilmDate,
            binding.contentEndTopFilmDate,
            binding.contentStartBottomFilmDate,
            binding.contentEndBottomFilmDate
        )
    }

    // Инициализация рейтингов найденных фильмов
    private fun initFilmsRaitings() {
        filmsRaitings = listOf(
            binding.contentStartTopFilmRaitingCircle,
            binding.contentEndTopFilmRaitingCircle,
            binding.contentStartBottomFilmRaitingCircle,
            binding.contentEndBottomFilmRaitingCircle
        )

        filmsRaitingsNumbers = listOf(
            binding.contentStartTopFilmRaitingNumber,
            binding.contentEndTopFilmRaitingNumber,
            binding.contentStartBottomFilmRaitingNumber,
            binding.contentEndBottomFilmRaitingNumber
        )
    }

}