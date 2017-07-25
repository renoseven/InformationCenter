package net.renoseven.informationcenter.module.MailForwarding;

import android.content.Context;

import net.grandcentrix.tray.TrayPreferences;
import net.renoseven.framework.FilteredBroadcastReceiver;
import net.renoseven.informationcenter.core.message.MessageHolder;
import net.renoseven.informationcenter.core.service.ServiceModule;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Mail Forwarding Module
 * Created by RenoSeven on 2017/7/25.
 */

public class MailForwardingModule implements ServiceModule {
    private final static String TAG = MailForwardingModule.class.getName();

    @Override
    public String getModuleName() {
        return TAG;
    }

    @Override
    public Set<FilteredBroadcastReceiver> getReceivers() {
        Set<FilteredBroadcastReceiver> receiverSet = new HashSet<>();
        receiverSet.add(new MailForwardingStateReceiver());
        return receiverSet;
    }

    @Override
    public Set<Runnable> getProcessors(Context context, Map<String, TrayPreferences> preferencesMap, MessageHolder message) {
        Set<Runnable> processorSet = new HashSet<>();
        processorSet.add(new MailForwardingProcessor(context, preferencesMap, message));
        return processorSet;
    }
}
