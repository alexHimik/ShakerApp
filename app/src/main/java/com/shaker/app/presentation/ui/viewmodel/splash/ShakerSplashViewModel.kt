package com.shaker.app.presentation.ui.viewmodel.splash

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.shaker.app.ShakerApplication
import com.shaker.domain.storage.ShakerPreferenceStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShakerSplashViewModel @Inject constructor(
    private val shakerPreferenceStorage: ShakerPreferenceStorage
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    fun displaySplashScreen() {
        viewModelScope.launch {
            delay(2000)
            _isLoading.value = false
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val shakerPreferences = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ShakerApplication).appComponent.getShakerPreferenceStore()
                ShakerSplashViewModel(shakerPreferences)
            }
        }
    }
}