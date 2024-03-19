package com.shaker.app.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import com.shaker.app.ShakerApplication
import com.shaker.app.presentation.di.module.ActivityModule
import javax.inject.Inject

open class ShakerBaseActivity : AppCompatActivity(), HasDefaultViewModelProviderFactory {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val defaultViewModelProviderFactory: ViewModelProvider.Factory
        get() = viewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as ShakerApplication).appComponent.presentationComponent(
            ActivityModule(this)).inject(this)
        super.onCreate(savedInstanceState)
    }
}