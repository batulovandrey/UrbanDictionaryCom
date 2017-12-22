package com.github.batulovandrey.unofficialurbandictionary.model

import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.UrbanDictionaryApp
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionAdapter
import com.github.batulovandrey.unofficialurbandictionary.adapter.DefinitionClickListener
import com.github.batulovandrey.unofficialurbandictionary.bean.BaseResponse
import com.github.batulovandrey.unofficialurbandictionary.bean.DefinitionResponse
import com.github.batulovandrey.unofficialurbandictionary.presenter.MainPresenter
import com.github.batulovandrey.unofficialurbandictionary.realm.RealmManager
import com.github.batulovandrey.unofficialurbandictionary.service.UrbanDictionaryService
import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * @author Andrey Batulov on 22/12/2017
 */


class MainModel(private val mMainPresenter: MainPresenter) {

    @Inject
    lateinit var mRealmManager: RealmManager

    @Inject
    lateinit var mService: UrbanDictionaryService

    private val mRealm: Realm
    private var mDefinitionAdapter: DefinitionAdapter? = null
    private var mDefinitions: List<DefinitionResponse>? = null

    init {
        UrbanDictionaryApp.getNetComponent().inject(this)
        mRealm = mRealmManager.realmDefinitions
    }

    fun textChanged(text: String): Boolean {
        if (mDefinitions != null && mDefinitions!!.isNotEmpty() && text.isEmpty()) {
            mMainPresenter.showDataInRecycler()
        } else {
            mMainPresenter.showQueriesInListView()
            mMainPresenter.filterText(text)
        }
        return false
    }

    fun getData(query: String, listener: DefinitionClickListener) {
        mMainPresenter.showProgressbar()
        val call = mService.getDefine(query)
        call.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>,
                                    response: Response<BaseResponse>) {
                if (response.body() != null) {
                    mDefinitions = response.body()!!.definitionResponses
                    mDefinitionAdapter = DefinitionAdapter(mDefinitions!!, listener)
                    mMainPresenter.setAdapterToRecycler(mDefinitionAdapter!!)
                    mMainPresenter.hideProgressbar()
                }
            }

            override fun onFailure(call: Call<BaseResponse>,
                                   t: Throwable) {
                println(call.toString())
                println(t.message)
                mMainPresenter.showToast(R.string.error)
                mMainPresenter.hideProgressbar()
            }
        })
    }

    fun getDefinitionId(position: Int): Long {
        saveDefinitionToRealm(position)
        return mDefinitions!![position].defid
    }

    private fun saveDefinitionToRealm(position: Int) {
        val defId = mDefinitions!![position].defid
        val checkDef = mRealm.where(DefinitionResponse::class.java).equalTo("defid", defId).findFirst()
        if (checkDef == null) {
            mRealm.executeTransaction { realm ->
                val definition = realm.createObject(DefinitionResponse::class.java)
                definition.author = mDefinitions!![position].author
                definition.defid = mDefinitions!![position].defid
                definition.definition = mDefinitions!![position].definition
                definition.example = mDefinitions!![position].example
                definition.permalink = mDefinitions!![position].permalink
                definition.thumbsDown = mDefinitions!![position].thumbsDown
                definition.thumbsUp = mDefinitions!![position].thumbsUp
                definition.word = mDefinitions!![position].word
            }
        }
    }
}