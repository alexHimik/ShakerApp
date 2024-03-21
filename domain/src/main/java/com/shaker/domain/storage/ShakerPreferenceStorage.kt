package com.shaker.domain.storage

interface ShakerPreferenceStorage {

    fun isFirstAppStart(): Boolean

    fun setFirstAppStartDone()

    fun getCategoriesUpdateStamp(): Long

    fun setCategoriesUpdateStamp(stamp: Long)

    fun clear()
}