package com.shaker.app.presentation.ui.viewmodel.onboarding

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shaker.app.presentation.ui.navigation.ShakerScreen
import com.shaker.domain.storage.ShakerPreferenceStorage
import kotlinx.coroutines.launch
import javax.inject.Inject

class OnBoardingViewModel @Inject constructor(
    private val shakerPreferenceStorage: ShakerPreferenceStorage
) : ViewModel() {

    private val _startDestination: MutableState<String> = mutableStateOf(
        if(!shakerPreferenceStorage.isFirstAppStart()) {
            ShakerScreen.OnBoarding.route
        } else {
            ShakerScreen.HomeRoot.route
        }
    )
    val startDestination: State<String> = _startDestination


    fun saveOnBoardingState(completed: Boolean) {
        viewModelScope.launch {
            if(completed) shakerPreferenceStorage.setFirstAppStartDone()
        }
    }
}