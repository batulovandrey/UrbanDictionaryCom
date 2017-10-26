package com.github.batulovandrey.urbandictionarycom.bean;

/**
 * @author Andrey Batulov on 26/10/2017
 */

public class UserQuery {

    private String query;

    public UserQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}