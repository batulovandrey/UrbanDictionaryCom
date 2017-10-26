package com.github.batulovandrey.urbandictionarycom.realm;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author Andrey Batulov on 26/10/2017
 */

public class RealmManager {

    private Realm mRealm;

    public RealmManager(Context context, String nameDb) {
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(nameDb + ".realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        mRealm = Realm.getInstance(config);
    }

    public Realm getRealm() {
        return mRealm;
    }
}