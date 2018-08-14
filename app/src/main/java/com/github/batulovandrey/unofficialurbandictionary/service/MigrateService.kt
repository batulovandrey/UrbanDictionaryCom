package com.github.batulovandrey.unofficialurbandictionary.service

import android.app.IntentService
import android.content.Intent
import com.github.batulovandrey.unofficialurbandictionary.UrbanDictionaryApp
import com.github.batulovandrey.unofficialurbandictionary.bean.DefinitionResponse
import com.github.batulovandrey.unofficialurbandictionary.bean.UserQuery
import com.github.batulovandrey.unofficialurbandictionary.data.DataManager
import com.github.batulovandrey.unofficialurbandictionary.realm.RealmManager
import com.github.batulovandrey.unofficialurbandictionary.utils.convertToDefinitionList
import com.github.batulovandrey.unofficialurbandictionary.utils.convertToUserQueriesList
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import javax.inject.Inject

class MigrateService : IntentService("migrateService") {

    @Inject
    lateinit var dataManager: DataManager

    override fun onCreate() {
        super.onCreate()
        UrbanDictionaryApp.getNetComponent().inject(this)
    }

    override fun onHandleIntent(intent: Intent?) {
        val realmManager = RealmManager(this)
        migrateData(realmManager.realmDefinitions)
        migrateData(realmManager.realmQueries)
    }

    private fun migrateData(realm: Realm) {
        val realmDbName = realm.configuration.realmFileName

        when (realmDbName) {

            "definitions.realm" -> {
                migrateDefinitions(realm, DefinitionResponse::class.java)
            }

            "favorites.realm" -> {
                migrateFavoriteDefinitions(realm, DefinitionResponse::class.java)

            }

            "queries.realm" -> {
                migrateUserQueries(realm, UserQuery::class.java)
            }
        }
    }

    private fun migrateDefinitions(realm: Realm, java: Class<DefinitionResponse>) {
        val realmResults = realm.where(java).findAll()

        if (realmResults == null || realmResults.size == 0) return

        val definitions = realmResults.convertToDefinitionList()
        for (definition in definitions) {
            dataManager.saveDefinition(definition)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
        }

        clearRealm(realm)
    }

    private fun migrateFavoriteDefinitions(realm: Realm, java: Class<DefinitionResponse>) {
        val realmResults = realm.where(java).findAll()

        if (realmResults == null || realmResults.size == 0) return

        val favorites = realmResults.convertToDefinitionList()
        for (favoriteDefinition in favorites) {
            dataManager.saveDefinitionToFavorites(favoriteDefinition)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
        }

        clearRealm(realm)
    }

    private fun migrateUserQueries(realm: Realm, java: Class<UserQuery>) {
        val realmResults = realm.where(java).findAll()

        if (realmResults == null || realmResults.size == 0) return

        val userQueries = realmResults.convertToUserQueriesList()
        for (savedUserQuery in userQueries) {
            dataManager.saveQuery(savedUserQuery)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
        }

        clearRealm(realm)
    }

    private fun clearRealm(realm: Realm) {
        realm.executeTransaction { rlm -> rlm.deleteAll() }
    }
}