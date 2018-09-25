package com.github.batulovandrey.unofficialurbandictionary.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.github.batulovandrey.unofficialurbandictionary.R

const val PRIVACY_POLICY_ACCEPTED = "privacy_policy_accepted"
const val IS_USER_CHOICE = "user_choice"

class PolicyDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.policy_dialog, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view?.findViewById<Button>(R.id.no_button)?.setOnClickListener { clickNo() }
        view?.findViewById<Button>(R.id.yes_button)?.setOnClickListener { clickYes() }
    }

    private fun clickNo() {
        writeToShared(false)
        dialog.dismiss()
    }

    private fun clickYes() {
        writeToShared(true)
        dialog.dismiss()
    }

    private fun writeToShared(value: Boolean) {
        val sharedPreferences = activity.getSharedPreferences(activity.packageName, Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putBoolean(PRIVACY_POLICY_ACCEPTED, value)
            putBoolean(IS_USER_CHOICE, true)
            apply()
        }
    }
}