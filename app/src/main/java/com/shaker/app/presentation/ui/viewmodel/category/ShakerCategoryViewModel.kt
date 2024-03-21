package com.shaker.app.presentation.ui.viewmodel.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.shaker.app.ShakerApplication
import com.shaker.domain.either.Either
import com.shaker.domain.model.ShakerCocktailCategoryModel
import com.shaker.domain.repository.cocktail.ShakerCocktailRepository
import com.shaker.domain.result.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShakerCategoryViewModel @Inject constructor(
    private val shakerCocktailRepository: ShakerCocktailRepository
) : ViewModel() {

    init {
        fetchCategories()
    }

    private val categoriesFlow: MutableStateFlow<CategoryResultsStateWrapper> = MutableStateFlow(
        CategoryResultsStateWrapper(loading = true, categories = emptyList()))
    private val handledErrors: MutableStateFlow<List<Failure>> =
        MutableStateFlow(emptyList())
    val categoriesData: StateFlow<CategoryResultsStateWrapper> get() = categoriesFlow
    val errorData: StateFlow<List<Failure>> get() = handledErrors

    private fun fetchCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val categoriesResult = shakerCocktailRepository.getCocktailsCategories()
            launch(Dispatchers.Main) {
                if(categoriesResult.isRight) {
                    categoriesFlow.value = CategoryResultsStateWrapper(false,
                        (categoriesResult as Either.Right).b)
                } else {
                    handledErrors.value = listOf((categoriesResult as Either.Left).a)
                }
            }
        }
    }

    data class CategoryResultsStateWrapper(
        val loading: Boolean,
        val categories: List<ShakerCocktailCategoryModel>
    )
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val shakerRepo = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ShakerApplication).appComponent.getShakerRepository()
                ShakerCategoryViewModel(shakerRepo)
            }
        }
    }
}