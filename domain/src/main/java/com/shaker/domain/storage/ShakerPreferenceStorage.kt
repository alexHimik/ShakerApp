package com.shaker.domain.storage

interface ShakerPreferenceStorage {

    fun isFirstAppStart(): Boolean

    fun setFirstAppStartDone()

    fun getCategoriesUpdateStamp(): Long

    fun getCategoriesUpdateStamp(stamp: Long)

    fun clear()
}