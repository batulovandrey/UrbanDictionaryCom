package com.github.batulovandrey.unofficialurbandictionary.ui

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.utils.DARK_THEME
import com.github.batulovandrey.unofficialurbandictionary.utils.ThemesManager
import com.github.batulovandrey.unofficialurbandictionary.utils.URBAN_THEME

class SettingsDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val themesManager = ThemesManager(activity)

        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.settings)
                .setItems(R.array.themes_array) { dialog, which ->
                    if (which == 0) {
                        themesManager.saveTheme(URBAN_THEME)
                        dialog.dismiss()
                        activity.recreate()
                    }
                    if (which == 1) {
                        themesManager.saveTheme(DARK_THEME)
                        dialog.dismiss()
                        activity.recreate()
                    }
                }
        return builder.create()
    }
}