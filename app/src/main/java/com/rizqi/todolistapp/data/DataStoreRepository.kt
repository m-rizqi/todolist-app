package com.rizqi.todolist.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

const val PREFERENCE_NAME = "ToDoListApp"

class DataStoreRepository(context: Context) {
    private object PreferenceKeys {
        val login = preferencesKey<Boolean>("login")
        val userId = preferencesKey<String>("user_id")
    }
    private val dataStore : DataStore<Preferences> = context.createDataStore(
        name = PREFERENCE_NAME
    )
    suspend fun setLogin(login : Boolean){
        dataStore.edit { preference ->
            preference[PreferenceKeys.login] = login
        }
    }
    val isLogin: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if(exception is IOException){
                Log.d("DataStore", exception.message.toString())
                emit(emptyPreferences())
            }else {
                throw exception
            }
        }
        .map { preference ->
            val login = preference[PreferenceKeys.login] ?: false
            login
        }

    suspend fun setUserId(userId : String){
        dataStore.edit { preference ->
            preference[PreferenceKeys.userId] = userId
        }
    }
    val getUserId: Flow<String> = dataStore.data
        .catch { exception ->
            if(exception is IOException){
                Log.d("DataStore", exception.message.toString())
                emit(emptyPreferences())
            }else {
                throw exception
            }
        }
        .map { preference ->
            val userId = preference[PreferenceKeys.userId] ?: "0"
            userId
        }

}