package com.github.batulovandrey.unofficialurbandictionary.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate.MODE_NIGHT_NO
import android.support.v7.app.AppCompatDelegate.MODE_NIGHT_YES
import com.github.batulovandrey.unofficialurbandictionary.R

class SettingsDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val sharedPreferences = activity.getSharedPreferences(getString(R.string.settings), Context.MODE_PRIVATE)

        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.settings)
                .setItems(R.array.themes_array) { dialog, which ->
                    if (which == 0) {
                        val editor = sharedPreferences.edit()
                        activity.setTheme(R.style.UrbanTheme)
                        editor.putInt(getString(R.string.theme), MODE_NIGHT_NO)
                        editor.apply()
                        dialog.dismiss()
                        activity.recreate()
                    }
                    if (which == 1) {
                        (activity as AppCompatActivity).delegate.setLocalNightMode(MODE_NIGHT_YES)
                        val editor = sharedPreferences.edit()
//                        activity.setTheme(R.style.UrbanBlackTheme)
                        editor.putInt(getString(R.string.theme), MODE_NIGHT_YES)
                        editor.apply()
                        dialog.dismiss()
                        activity.recreate()
                    }
                }
        return builder.create()
    }
}