package com.example.submission_intermediete_dicoding.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map





val Context.datastore :DataStore<Preferences> by preferencesDataStore(name = "login")
class LoginPreference private constructor(private val dataStore: DataStore<Preferences>) {

    private val SESSION_KEY = booleanPreferencesKey("login_session")

    fun getLoginSession(): Flow<Boolean> {
        return dataStore.data.map { preference ->
            preference[SESSION_KEY] ?: false
        }
    }

    suspend fun saveLoginSession(isLoged : Boolean) {
        dataStore.edit { preference ->
        preference[SESSION_KEY] = isLoged
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: LoginPreference? =null

        fun getInstance(dataStore: DataStore<Preferences>): LoginPreference {
            return INSTANCE ?: synchronized(this) {
                val instance =LoginPreference(dataStore)
                INSTANCE =  instance
                instance
            }
        }
    }
}