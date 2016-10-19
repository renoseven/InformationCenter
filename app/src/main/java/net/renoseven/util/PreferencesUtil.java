package net.renoseven.util;

import net.grandcentrix.tray.TrayPreferences;
import net.grandcentrix.tray.core.TrayItem;

import java.util.Collection;
import java.util.Properties;

/**
 * Preferences Util
 * Created by RenoSeven on 2016/9/12.
 */
public abstract class PreferencesUtil {

    public static void convert(final TrayPreferences from, final Properties to) {
        to.clear();
        Collection<TrayItem> records = from.getAll();
        for (TrayItem record : records) {
            to.setProperty(record.key(), record.value());
        }
    }
}