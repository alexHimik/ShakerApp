package com.shaker.app.presentation.ui.viewmodel.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.shaker.app.ShakerApplication
import com.shaker.domain.either.Either
import com.shaker.domain.model.ShakerCocktailModel
import com.shaker.domain.repository.cocktail.ShakerCocktailRepository
import com.shaker.domain.result.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SEARCH = "search"

class ShakerSearchViewModel @Inject constructor(
    private val shakerCocktailRepository: ShakerCocktailRepository,
    private var savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val availableCocktails: MutableStateFlow<List<ShakerCocktailModel>> =
        MutableStateFlow(emptyList())
    private val lastViewedCocktails: MutableStateFlow<List<ShakerCocktailModel>> =
        MutableStateFlow(emptyList())
    private val handledErrors: MutableStateFlow<List<Failure>> =
        MutableStateFlow(emptyList())
    private val progressValue: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val searchProgress: StateFlow<Boolean> get() = progressValue
    val query = savedStateHandle.getStateFlow(SEARCH, "")

    val displayResults: StateFlow<SearchResultsStateWrapper> = query
        .debounce(500L)
        .flatMapLatest { text ->
            val resultFlow = combine(
                availableCocktails,
                lastViewedCocktails
            ) { available, viewed ->
                return@combine if (available.isNotEmpty()) {
                    SearchResultsStateWrapper(
                        SearchDisplay.Results,
                        available
                    )
                } else if (viewed.isNotEmpty() && available.isEmpty()) {
                    SearchResultsStateWrapper(
                        SearchDisplay.Viewed,
                        viewed
                    )
                } else {
                    SearchResultsStateWrapper(
                        SearchDisplay.NoResults,
                        emptyList()
                    )
                }
            }
            //reduce searched cocktails amount
            if(text.length > 1) {
                progressValue.value = true
                startCocktailSearch(text)
            }
            return@flatMapLatest resultFlow
        }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            SearchResultsStateWrapper(SearchDisplay.NoResults, emptyList())
        )

    val errorData: StateFlow<List<Failure>> get() = handledErrors

    init {
        fetchLastViewedCocktails()
    }

    fun onSearchChange(searchText: String) {
        savedStateHandle[SEARCH] = searchText
        if(searchText.isEmpty()) {
            fetchLastViewedCocktails()
            availableCocktails.value = emptyList()
        } else {
            progressValue.value = true
        }
    }

    private suspend fun startCocktailSearch(text: String) {
        progressValue.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = shakerCocktailRepository.searchCocktailByName(text)
            if(result.isRight) {
                launch(Dispatchers.Main) {
                    availableCocktails.value = (result as Either.Right).b
                    progressValue.value = false
                }
            } else {
                handledErrors.value = listOf((result as Either.Left).a)
            }
        }
    }

    private fun fetchLastViewedCocktails() {
        progressValue.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = shakerCocktailRepository.getLastViewedCocktails()
            if (result.isRight) {
                launch(Dispatchers.Main) {
                    lastViewedCocktails.value = (result as Either.Right).b
                    progressValue.value = false
                }
            } else {
                handledErrors.value = listOf((result as Either.Left).a)
            }
        }
    }

    enum class SearchDisplay {
        Viewed, Results, NoResults
    }
    data class SearchResultsStateWrapper(
        val resultsType: SearchDisplay,
        val results: List<ShakerCocktailModel>
    )

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val shakerRepo = (this[APPLICATION_KEY] as ShakerApplication).appComponent.getShakerRepository()
                ShakerSearchViewModel(shakerRepo, savedStateHandle)
            }
        }
    }
}