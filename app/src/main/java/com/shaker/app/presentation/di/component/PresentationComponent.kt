package com.shaker.app.presentation.di.component

import com.shaker.app.core.ShakerBaseActivity
import com.shaker.app.presentation.di.module.ActivityModule
import com.shaker.app.presentation.di.module.ViewModelModule
import com.shaker.app.presentation.di.scope.PresentationScope
import com.shaker.app.presentation.ui.screen.onboarding.di.ShakerOnBoardingModule
import com.shaker.app.presentation.ui.screen.search.di.ShakerSearchModule
import com.shaker.app.presentation.ui.screen.splash.di.ShakerSplashModule
import com.shaker.domain.storage.ShakerPreferenceStorage
import dagger.Subcomponent

@PresentationScope
@Subcomponent(
    modules = [
        ActivityModule::class,
        ViewModelModule::class,
        ShakerOnBoardingModule::class,
        ShakerSplashModule::class,
        ShakerSearchModule::class
    ]
)
interface PresentationComponent {

    fun inject(baseActivity: ShakerBaseActivity)
}