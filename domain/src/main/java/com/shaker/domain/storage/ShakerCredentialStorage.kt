package com.shaker.domain.storage

interface ShakerCredentialStorage {

    suspend fun provideApiCredentials(): String

}