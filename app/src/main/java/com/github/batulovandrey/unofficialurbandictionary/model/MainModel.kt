package com.github.batulovandrey.unofficialurbandictionary.model

import com.github.batulovandrey.unofficialurbandictionary.R
import com.github.batulovandrey.unofficialurbandictionary.UrbanDictionaryApp
import com.github.batulovandrey.unofficialurbandictionary.adapter.*
import com.github.batulovandrey.unofficialurbandictionary.bean.DefinitionResponse
import com.github.batulovandrey.unofficialurbandictionary.bean.UserQuery
import com.github.batulovandrey.unofficialurbandictionary.presenter.MainPresenter
import com.github.batulovandrey.unofficialurbandictionary.realm.RealmManager
import com.github.batulovandrey.unofficialurbandictionary.service.UrbanDictionaryService
import io.realm.Realm
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Andrey Batulov on 22/12/2017
 */

class MainModel(private val mMainPresenter: MainPresenter) : QueriesClickListener {

    @Inject
    lateinit var mRealmManager: RealmManager

    @Inject
    lateinit var mService: UrbanDictionaryService

    private lateinit var mDefinitionAdapter: DefinitionAdapter
    private var mDefinitions: List<DefinitionResponse>? = null
    private val mDefinitionsRealm: Realm
    private val mUserQueriesRealm: Realm
    private var mUserQueriesAdapter: QueriesAdapter
    private var mQueries: List<UserQuery>

    init {
        UrbanDictionaryApp.getNetComponent().inject(this)
        mDefinitionsRealm = mRealmManager.realmDefinitions
        mUserQueriesRealm = mRealmManager.realmQueries
        mQueries = mUserQueriesRealm.where(UserQuery::class.java).findAll()
        mUserQueriesAdapter = QueriesAdapter(mQueries, this)
    }

    override fun onQueryClick(position: Int) {
        val query = mQueries[position]
        mMainPresenter.initializeQueryToServer(query.query)
    }

    override fun deleteQueryFromRealm(position: Int) {
        mUserQueriesRealm.executeTransaction({ realm ->
            val result = realm.where(UserQuery::class.java).equalTo("query", mQueries[position].query).findFirst()
            result.deleteFromRealm()
        })
        mUserQueriesAdapter.notifyDataSetChanged()
    }

    fun textChanged(text: String): Boolean {
        if (mDefinitions != null && mDefinitions!!.isNotEmpty() && text.isEmpty()) {
            mMainPresenter.showDefinitionsInRecycler()
        } else {
            filterUserQueries(text)
        }
        return false
    }

    fun getData(query: String, listener: DefinitionClickListener) {
        mMainPresenter.showProgressbar()
        mService.getDefineRx(query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    mDefinitions = result.definitionResponses
                    mDefinitionAdapter = DefinitionAdapter(mDefinitions!!, listener)
                    mMainPresenter.setAdapterToDefinitionsRecycler(mDefinitionAdapter)
                    mMainPresenter.hideProgressbar()
                }, { error ->
                    print(error.message)
                    mMainPresenter.showToast(R.string.error)
                    mMainPresenter.hideProgressbar()
                })
    }

    fun getDefinitionId(position: Int): Long {
        saveDefinitionToRealm(position)
        return mDefinitions?.get(position)!!.defid
    }

    fun saveQueryToRealm(query: String) {
        val isExist = mQueries.any { it.query == query.toLowerCase() }
        if (!isExist) {
            mUserQueriesRealm.executeTransaction({ realm ->
                val userQuery = realm.createObject(UserQuery::class.java)
                userQuery.query = query.toLowerCase()
            })
            mUserQueriesAdapter.notifyDataSetChanged()
        }
    }

    private fun filterUserQueries(text: String) {
        mQueries = if (text.isNotEmpty()) {
            mUserQueriesRealm.where(UserQuery::class.java).contains("query", text.toLowerCase()).findAll()
        } else {
            mUserQueriesRealm.where(UserQuery::class.java).findAll()
        }
        mUserQueriesAdapter = QueriesAdapter(mQueries, this)
        mMainPresenter.setAdapterToQueriesRecycler(mUserQueriesAdapter)
        mMainPresenter.showQueriesInRecycler()
    }

    private fun saveDefinitionToRealm(position: Int) {
        val defId = mDefinitions?.get(position)?.defid
        val checkDef = mDefinitionsRealm.where(DefinitionResponse::class.java).equalTo("defid", defId).findFirst()
        if (checkDef == null) {
            mDefinitionsRealm.executeTransaction { realm ->
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