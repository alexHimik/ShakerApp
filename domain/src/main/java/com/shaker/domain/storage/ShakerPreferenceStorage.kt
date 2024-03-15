package com.shaker.domain.storage

interface ShakerPreferenceStorage {

    fun isFirstAppStart(): Boolean

    fun setFirstAppStartDone()

    fun clear()
}