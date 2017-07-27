package net.renoseven.informationcenter.module.smsmonitor;

import android.content.Context;

import net.grandcentrix.tray.TrayPreferences;
import net.renoseven.framework.FilteredBroadcastReceiver;
import net.renoseven.informationcenter.core.message.Message;
import net.renoseven.informationcenter.core.service.ServiceModule;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * SMS Forwarding Module
 * Created by RenoSeven on 2017/7/25.
 */

public class SMSMonitorModule implements ServiceModule {
    private final static String TAG = SMSMonitorModule.class.getName();

    @Override
    public String getModuleName() {
        return TAG;
    }

    @Override
    public Set<FilteredBroadcastReceiver> getReceivers() {
        Set<FilteredBroadcastReceiver> receiverSet = new HashSet<>();
        receiverSet.add(new SMSMonitor());
        return receiverSet;
    }

    @Override
    public Set<Runnable> getProcessors(Context context, Map<String, TrayPreferences> preferencesMap, Message message) {
        return new HashSet<>();
    }
}