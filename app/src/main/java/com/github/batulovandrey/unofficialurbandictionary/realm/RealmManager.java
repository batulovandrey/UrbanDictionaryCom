package com.github.batulovandrey.unofficialurbandictionary.realm;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author Andrey Batulov on 26/10/2017
 */

public class RealmManager {

    public RealmManager(Context context) {
        Realm.init(context);
    }

    public Realm getRealmDefinitions() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("definitions.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        return Realm.getInstance(config);
    }

    public Realm getRealmFavorites() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("favorites.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        return Realm.getInstance(config);
    }

    public Realm getRealmQueries() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("queries.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        return Realm.getInstance(config);
    }
}