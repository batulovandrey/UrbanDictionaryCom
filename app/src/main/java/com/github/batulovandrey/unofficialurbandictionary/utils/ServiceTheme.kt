package com.github.batulovandrey.unofficialurbandictionary.utils

import android.support.annotation.StringDef

@Retention(AnnotationRetention.SOURCE)
@StringDef(URBAN_THEME, DARK_THEME)
annotation class ServiceTheme

const val URBAN_THEME = "urban"
const val DARK_THEME = "dark"