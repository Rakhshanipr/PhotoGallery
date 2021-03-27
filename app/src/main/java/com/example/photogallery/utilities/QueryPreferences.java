package com.example.photogallery.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class QueryPreferences {

    public static final String PREF_KEY_SEARCH="searchQuery";

    public static void setSearchQuery(Context context,String value){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(PREF_KEY_SEARCH,value);

        editor.apply();
    }

    public static String getSearchQuery(Context context){
        String string=PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_KEY_SEARCH,null);

        return string;
    }
}