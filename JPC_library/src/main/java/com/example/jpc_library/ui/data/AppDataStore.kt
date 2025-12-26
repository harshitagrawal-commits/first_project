package com.example.jpc_library.ui.data

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.intPreferencesKey


//val Context.dataStore by preferencesDataStore(name = "app_prefs")
/*
object PrefKeys {

    val THEME_INDEX = intPreferencesKey("theme_index")   // ✅ ADD THIS
    //val DARK_THEME = booleanPreferencesKey("dark_theme") // optional
    //val SELECTED = booleanPreferencesKey("selected")     // optional
}
*/
val Context.dataStore by preferencesDataStore(name = "app_prefs")

object PrefKeys {
    val THEME_INDEX = intPreferencesKey("theme_index")
}

