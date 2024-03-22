package com.shaker.app.presentation.ui.viewmodel.cocktails.details

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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShakerCocktailDetailsViewModel @Inject constructor(
    private val shakerCocktailRepository: ShakerCocktailRepository
) : ViewModel() {

    private val cocktailDataResult: MutableStateFlow<CocktailDetailsStateWrapper> =
        MutableStateFlow(CocktailDetailsStateWrapper(true, null))
    private val handledErrors: MutableStateFlow<List<Failure>> =
        MutableStateFlow(emptyList())
    private val isRefreshingValue = MutableStateFlow(false)
    private val isFavouriteValue = MutableStateFlow(false)


    val isRefreshing: StateFlow<Boolean> get() = isRefreshingValue.asStateFlow()
    val isFavourite: StateFlow<Boolean> get() = isFavouriteValue.asStateFlow()
    val cocktailData: StateFlow<CocktailDetailsStateWrapper> get() = cocktailDataResult
    val errorData: StateFlow<List<Failure>> get() = handledErrors

    fun getCocktailDetails(cocktailId: String?) {
        viewModelScope.launch {
            if(cocktailId?.isNotEmpty() == true) {
                withContext(Dispatchers.IO) {
                    cocktailDataResult.emit(CocktailDetailsStateWrapper(true, null))
                    val cocktailResult = shakerCocktailRepository.getCocktailsDetails(cocktailId)
                    if(cocktailResult.isRight) {
                        shakerCocktailRepository.addCocktailToLastViewed((cocktailResult as Either.Right).b)
                    }
                    withContext(Dispatchers.Main) {
                        if(cocktailResult.isRight) {
                            cocktailDataResult.emit(CocktailDetailsStateWrapper(false,
                                (cocktailResult as Either.Right).b))
                        } else {
                            cocktailDataResult.emit(CocktailDetailsStateWrapper(false, null))
                            handledErrors.emit(listOf((cocktailResult as Either.Left).a))
                        }
                    }
                }
            } else {
                cocktailDataResult.emit(CocktailDetailsStateWrapper(false, null))
            }
        }
    }

    fun getRandomCocktailDetails() {
        viewModelScope.launch {
            isRefreshingValue.emit(true)
            withContext(Dispatchers.IO) {
                val cocktailResult = shakerCocktailRepository.getRandomCocktailDetails()
                if(cocktailResult.isRight) {
                    shakerCocktailRepository.addCocktailToLastViewed((cocktailResult as Either.Right).b)
                }
                withContext(Dispatchers.Main) {
                    isRefreshingValue.emit(false)
                    if(cocktailResult.isRight) {
                        cocktailDataResult.value = CocktailDetailsStateWrapper(false,
                            (cocktailResult as Either.Right).b)
                    } else {
                        cocktailDataResult.emit(CocktailDetailsStateWrapper(false, null))
                        handledErrors.value = listOf((cocktailResult as Either.Left).a)
                    }
                }
            }
        }
    }

    fun addCocktailToFavourites(cocktail: ShakerCocktailModel) {
        viewModelScope.launch {
            isFavouriteValue.value = !isFavouriteValue.value
        }
    }

    data class CocktailDetailsStateWrapper(
        val loading: Boolean,
        val cocktail: ShakerCocktailModel?
    )
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val shakerRepo = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ShakerApplication).appComponent.getShakerRepository()
                ShakerCocktailDetailsViewModel(shakerRepo)
            }
        }
    }
}