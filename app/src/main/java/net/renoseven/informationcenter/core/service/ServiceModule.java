package net.renoseven.informationcenter.core.service;

import android.content.Context;

import net.grandcentrix.tray.TrayPreferences;
import net.renoseven.framework.FilteredBroadcastReceiver;
import net.renoseven.informationcenter.core.bean.Message;

import java.util.Map;
import java.util.Set;

/**
 * Service Module Interface
 * Created by RenoSeven on 2017/7/25.
 */

public interface ServiceModule {
    String getModuleName();

    Set<FilteredBroadcastReceiver> getReceivers();

    Set<Runnable> getProcessors(Context context, Map<String, TrayPreferences> preferencesMap, Message message);
}
