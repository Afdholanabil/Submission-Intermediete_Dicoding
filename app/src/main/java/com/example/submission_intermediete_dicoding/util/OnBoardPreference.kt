package com.example.submission_intermediete_dicoding.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "onboard")
class OnBoardPreference private constructor(private val dataStore:DataStore<Preferences>) {
    private val ONBOARD_KEY = booleanPreferencesKey("onboard_session")

    fun getOnBoardSession(): Flow<Boolean> {
        return dataStore.data.map { pref ->
            pref[ONBOARD_KEY] ?: false
        }
    }

    suspend fun saveOnboardSession(isAlreadyOnboard : Boolean) {
        dataStore.edit { pref ->
            pref[ONBOARD_KEY] = isAlreadyOnboard
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: OnBoardPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): OnBoardPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = OnBoardPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}