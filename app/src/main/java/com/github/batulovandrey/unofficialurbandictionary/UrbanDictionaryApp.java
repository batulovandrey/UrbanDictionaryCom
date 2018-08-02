package com.github.batulovandrey.unofficialurbandictionary;

import android.app.Application;

import com.github.batulovandrey.unofficialurbandictionary.bean.DefinitionResponse;
import com.github.batulovandrey.unofficialurbandictionary.bean.UserQuery;
import com.github.batulovandrey.unofficialurbandictionary.dagger.AppModule;
import com.github.batulovandrey.unofficialurbandictionary.dagger.DaggerNetComponent;
import com.github.batulovandrey.unofficialurbandictionary.dagger.NetComponent;
import com.github.batulovandrey.unofficialurbandictionary.dagger.NetModule;
import com.github.batulovandrey.unofficialurbandictionary.data.AppDataManager;
import com.github.batulovandrey.unofficialurbandictionary.data.DataManager;
import com.github.batulovandrey.unofficialurbandictionary.data.db.AppDbHelper;
import com.github.batulovandrey.unofficialurbandictionary.data.db.DbHelper;
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.Definition;
import com.github.batulovandrey.unofficialurbandictionary.data.db.model.SavedUserQuery;
import com.github.batulovandrey.unofficialurbandictionary.data.network.AppNetworkHelper;
import com.github.batulovandrey.unofficialurbandictionary.data.network.NetworkHelper;
import com.github.batulovandrey.unofficialurbandictionary.realm.RealmManager;

import java.util.List;

import io.realm.RealmResults;

import static com.github.batulovandrey.unofficialurbandictionary.utils.ExtensionsKt.convertToDefinitionList;
import static com.github.batulovandrey.unofficialurbandictionary.utils.ExtensionsKt.convertToUserQueriesList;

/**
 * @author Andrey Batulov on 30/10/2017
 */

public class UrbanDictionaryApp extends Application {

    private static final String BASE_URL = "http://api.urbandictionary.com/";
    private static NetComponent sNetComponent;

    private DataManager dataManager;
    private DbHelper dbHelper;
    private NetworkHelper networkHelper;

    public static NetComponent getNetComponent() {
        return sNetComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(BASE_URL))
                .build();

        dbHelper = new AppDbHelper(this);
        networkHelper = new AppNetworkHelper();
        dataManager = new AppDataManager(this, dbHelper, networkHelper);

        migrateDataFromRealmToRoom();
    }

    public final DataManager getDataManager() {
        return dataManager;
    }

    private void migrateDataFromRealmToRoom() {
        RealmManager realmManager = new RealmManager(this.getApplicationContext());
        RealmResults<DefinitionResponse> definitionsResults =
                realmManager.getRealmDefinitions().where(DefinitionResponse.class).findAll();

        if (definitionsResults != null && definitionsResults.size() > 0) {

            List<Definition> definitions = convertToDefinitionList(definitionsResults);
            for (Definition definition : definitions) {
                dbHelper.saveDefinition(definition);
            }

            realmManager.getRealmDefinitions().deleteAll();

        }

        RealmResults<DefinitionResponse> favoritesDefinitionsResuls =
                realmManager.getRealmFavorites().where(DefinitionResponse.class).findAll();

        if (favoritesDefinitionsResuls != null && favoritesDefinitionsResuls.size() > 0) {

            List<Definition> favorites = convertToDefinitionList(favoritesDefinitionsResuls);
            for (Definition favoriteDefinition : favorites) {
                dbHelper.saveDefinitionToFavorites(favoriteDefinition);
            }

            realmManager.getRealmFavorites().deleteAll();
        }

        RealmResults<UserQuery> userQueriesResults =
                realmManager.getRealmQueries().where(UserQuery.class).findAll();

        if (userQueriesResults != null && userQueriesResults.size() > 0) {

            List<SavedUserQuery> userQueries = convertToUserQueriesList(userQueriesResults);
            for (SavedUserQuery savedUserQuery : userQueries) {
                dbHelper.saveQuery(savedUserQuery);
            }

            realmManager.getRealmQueries().deleteAll();
        }
    }
}