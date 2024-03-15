package com.shaker.data.storage

import com.shaker.domain.storage.ShakerCredentialStorage

class ShakerCredentialStorageImpl : ShakerCredentialStorage {

    override suspend fun provideApiCredentials(): String {
        //return stub API key value for www.thecocktaildb.com
        return "1"
    }
}