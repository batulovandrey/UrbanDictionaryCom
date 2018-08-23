package com.github.batulovandrey.unofficialurbandictionary.data.bean;

import io.realm.RealmObject;

/**
 * @author Andrey Batulov on 26/10/2017
 */

public class UserQuery extends RealmObject {

    private String query;

    public UserQuery() {
        // needed by Realm
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}