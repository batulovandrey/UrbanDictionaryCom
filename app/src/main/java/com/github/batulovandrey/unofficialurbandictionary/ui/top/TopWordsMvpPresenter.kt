package com.github.batulovandrey.unofficialurbandictionary.ui.top

import com.github.batulovandrey.unofficialurbandictionary.presenter.MvpPresenter

interface TopWordsMvpPresenter<V: TopWordsMvpView>: MvpPresenter<V> {

    fun initializeAlphabet()

    fun updateWords(letter: String = "a")
}