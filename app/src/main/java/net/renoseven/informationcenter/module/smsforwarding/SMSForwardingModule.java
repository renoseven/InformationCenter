package net.renoseven.informationcenter.module.smsforwarding;

import android.content.Context;

import net.grandcentrix.tray.TrayPreferences;
import net.renoseven.framework.FilteredBroadcastReceiver;
import net.renoseven.informationcenter.core.message.MessageHolder;
import net.renoseven.informationcenter.core.service.ServiceModule;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * SMS Forwarding Module
 * Created by RenoSeven on 2017/7/25.
 */

public class SMSForwardingModule implements ServiceModule {
    private final static String TAG = SMSForwardingModule.class.getName();

    @Override
    public String getModuleName() {
        return TAG;
    }

    @Override
    public Set<FilteredBroadcastReceiver> getReceivers() {
        Set<FilteredBroadcastReceiver> receiverSet = new HashSet<>();
        receiverSet.add(new SMSForwardingStateReceiver());
        return receiverSet;
    }

    @Override
    public Set<Runnable> getProcessors(Context context, Map<String, TrayPreferences> preferencesMap, MessageHolder message) {
        Set<Runnable> processorSet = new HashSet<>();
        processorSet.add(new SMSForwardingProcessor(context, preferencesMap, message));
        return processorSet;
    }
}