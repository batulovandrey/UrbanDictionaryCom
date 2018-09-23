package com.github.batulovandrey.unofficialurbandictionary.utils

import android.content.Context
import com.github.batulovandrey.unofficialurbandictionary.R

private const val NAME = "themes"
private const val THEME = "theme"

class ThemesManager(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    fun setTheme() {
        when(sharedPreferences.getString(THEME, URBAN_THEME)) {

            URBAN_THEME -> context.setTheme(R.style.UrbanTheme)

            DARK_THEME -> context.setTheme(R.style.DarkTheme)
        }
    }

    fun saveTheme(@ServiceTheme theme: String) {
        sharedPreferences.edit()
                .putString(THEME, theme)
                .apply()

    }

    fun getTheme(): String {
        return sharedPreferences.getString(THEME, URBAN_THEME)
    }
}