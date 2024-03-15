package com.shaker.data.storage

import android.app.Activity
import android.content.Context
import com.shaker.domain.storage.ShakerPreferenceStorage
import javax.inject.Inject

class ShakerPreferenceStorageImpl @Inject constructor(context: Context) : ShakerPreferenceStorage {

    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun isFirstAppStart(): Boolean {
        return sharedPreferences.getBoolean(FIRST_APP_START_DONE, false)
    }

    override fun setFirstAppStartDone() {
        editor.putBoolean(FIRST_APP_START_DONE, true).apply()
    }

    override fun clear() {
        editor.clear()
    }

    companion object {
        private var _instance: ShakerPreferenceStorageImpl? = null

        private const val PREFS_NAME = "shaker_app_preferences"
        private const val FIRST_APP_START_DONE = "first_app_start_done"

        internal fun instance(context: Context): ShakerPreferenceStorageImpl {
            if (_instance == null) {
                _instance = ShakerPreferenceStorageImpl(context)
            }
            return _instance!!
        }
    }
}