package com.ibrajix.sabinews.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_storage")

@Singleton
class Storage @Inject constructor(@ApplicationContext context: Context) : StorageInterface {

    private val dataStore = context.dataStore

    private object PreferenceKeys {
        val SELECTED_THEME = booleanPreferencesKey("selected_theme")
    }

    override fun getSelectedTheme() = dataStore.data.catch {
        if (it is IOException){
            emit(emptyPreferences())
        }
        else{
            throw it
        }
    }.map {
        it[PreferenceKeys.SELECTED_THEME] ?: false
    }


    override suspend fun setSelectedTheme(theme: Boolean) {
        dataStore.edit {
            it[PreferenceKeys.SELECTED_THEME] = theme
        }
    }

}