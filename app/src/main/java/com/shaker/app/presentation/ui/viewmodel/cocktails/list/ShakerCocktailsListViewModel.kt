package com.shaker.app.presentation.ui.viewmodel.cocktails.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShakerCocktailsListViewModel @Inject constructor(
    private val shakerCocktailRepository: ShakerCocktailRepository
) : ViewModel() {

    private val forCategoryCocktails: MutableStateFlow<CategoryResultsStateWrapper> =
        MutableStateFlow(CategoryResultsStateWrapper(true, emptyList()))
    private val handledErrors: MutableStateFlow<List<Failure>> =
        MutableStateFlow(emptyList())

    val categoryCocktails: StateFlow<CategoryResultsStateWrapper> get() = forCategoryCocktails
    val errorData: StateFlow<List<Failure>> get() = handledErrors

    fun getCocktailsForCategory(categoryName: String) {
        if(categoryName.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                val categoryResult = shakerCocktailRepository.getCategoryCocktails(categoryName)
                launch(Dispatchers.Main) {
                    if(categoryResult.isRight) {
                        forCategoryCocktails.value = CategoryResultsStateWrapper(
                            false,
                            (categoryResult as Either.Right).b
                        )
                    } else {
                        handledErrors.value = listOf((categoryResult as Either.Left).a)
                        forCategoryCocktails.value = CategoryResultsStateWrapper(false, emptyList())
                    }
                }
            }
        } else {
            forCategoryCocktails.value = CategoryResultsStateWrapper(false, emptyList())
        }
    }

    data class CategoryResultsStateWrapper(
        val loading: Boolean,
        val cocktails: List<ShakerCocktailModel>
    )

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val shakerRepo = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ShakerApplication).appComponent.getShakerRepository()
                ShakerCocktailsListViewModel(shakerRepo)
            }
        }
    }
}