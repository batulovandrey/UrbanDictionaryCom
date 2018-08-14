package com.github.batulovandrey.unofficialurbandictionary.ui

interface MvpView {

    fun showLoading() {}

    fun hideLoading() {}

    fun hideKeyboard() {}

    fun isNetworkConnected() = false
}