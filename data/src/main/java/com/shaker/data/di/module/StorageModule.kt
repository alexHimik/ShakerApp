package com.shaker.data.di.module

import android.content.Context
import com.shaker.data.storage.ShakerCredentialStorageImpl
import com.shaker.data.storage.ShakerPreferenceStorageImpl
import com.shaker.domain.storage.ShakerCredentialStorage
import com.shaker.domain.storage.ShakerPreferenceStorage
import dagger.Module
import dagger.Provides

@Module
class StorageModule(val context: Context) {

    @Provides
    fun provideCredentialStorage(): ShakerCredentialStorage = ShakerCredentialStorageImpl()

    @Provides
    fun providePreferenceStorage(): ShakerPreferenceStorage = ShakerPreferenceStorageImpl(context)
}