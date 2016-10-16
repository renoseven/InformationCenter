package net.renoseven.util;

import android.content.SharedPreferences;

import java.util.Properties;

/**
 * Created by RenoSeven on 2016/9/12.
 */
public class PreferencesUtil {

    public static void convert(final Properties from, SharedPreferences to) {
        SharedPreferences.Editor editor = to.edit();
        editor.clear();
        for(String key : from.stringPropertyNames()) {
            editor.putString(key, from.getProperty(key));
        }
        editor.apply();
    }

    public static void convert(final SharedPreferences from, Properties to) {
        to.clear();
        for(String key : from.getAll().keySet()) {
            to.setProperty(key, from.getString(key, null));
        }
    }
}
