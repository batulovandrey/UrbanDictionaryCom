package com.github.batulovandrey.unofficialurbandictionary.ui.cached

import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionClickListener
import com.github.batulovandrey.unofficialurbandictionary.data.DataManager
import com.github.batulovandrey.unofficialurbandictionary.presenter.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CachedPresenter<V : CachedMvpView> @Inject constructor(dataManager: DataManager,
                                                             compositeDisposable: CompositeDisposable)
    : CachedMvpPresenter<V>,
        BasePresenter<V>(dataManager, compositeDisposable),
        DefinitionClickListener {

    private lateinit var definitionAdapter: DefinitionAdapter

    override fun loadData() {
        compositeDisposable.add(
                dataManager.getDefinitions()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe {
                            if (it.isNotEmpty()) {
                                definitionAdapter = DefinitionAdapter(it, this)
                                mvpView?.setDefinitionAdapter(definitionAdapter)
                                mvpView?.showData()
                            } else {
                                mvpView?.showPlaceHolder()
                            }
                        }
        )
    }

    override fun clearCache() {
        compositeDisposable.add(
                dataManager.deleteAllDefinitions()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe { mvpView?.showPlaceHolder() }
        )
    }

    override fun onItemClick(position: Int) {
        dataManager.setActiveDefinition(definitionAdapter.getDefinitionByPosition(position))
        mvpView?.showDetailFragment()
    }
}