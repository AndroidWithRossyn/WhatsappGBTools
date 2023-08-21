package com.statuswa.fasttalkchat.toolsdownload.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Prefrancemanager {
    public static String UriWp;

    public static String getUriWp(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("tree_uri", "");
    }

    public static void putUriWp(Context context, String str) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putString("tree_uri", str);
        edit.commit();
    }
}
