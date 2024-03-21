package com.shaker.app.presentation.ui.viewmodel.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.shaker.app.ShakerApplication
import com.shaker.app.presentation.ui.viewmodel.category.ShakerCategoryViewModel
import com.shaker.domain.repository.cocktail.ShakerCocktailRepository
import javax.inject.Inject

class ShakerFavouritesViewModel @Inject constructor(
    private val shakerCocktailRepository: ShakerCocktailRepository
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val shakerRepo = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ShakerApplication).appComponent.getShakerRepository()
                ShakerFavouritesViewModel(shakerRepo)
            }
        }
    }
}