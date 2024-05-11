package com.example.submission_intermediete_dicoding.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.submission_intermediete_dicoding.data.response.LoginResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.datastore :DataStore<Preferences> by preferencesDataStore(name = "login")
class LoginPreference private constructor(private val dataStore: DataStore<Preferences>) {

    private val TOKEN_KEY = stringPreferencesKey("login_token")
    private val LOGIN_RESPONSE_KEY = stringPreferencesKey("login_response")


    suspend fun saveLoginSession(token: LoginResponse) {
        val jsonString = Gson().toJson(token)
        dataStore.edit { preferences ->
            preferences[LOGIN_RESPONSE_KEY] = jsonString
        }

    }

    fun getLoginSession(): Flow<LoginResponse?> {
        return dataStore.data.map { preferences ->
            val jsonString = preferences[LOGIN_RESPONSE_KEY]
            Gson().fromJson(jsonString, LoginResponse::class.java)
        }
    }

    suspend fun removeSession() {
        dataStore.edit { pref ->
            pref.remove(LOGIN_RESPONSE_KEY)
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