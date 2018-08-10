package com.github.batulovandrey.unofficialurbandictionary.presenter

interface MvpView {

    fun showLoading() {}

    fun hideLoading() {}

    fun hideKeyboard() {}

    fun isNetworkConnected() = false
}