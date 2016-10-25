package net.renoseven.informationcenter.service;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.util.Log;

import net.grandcentrix.tray.TrayPreferences;
import net.renoseven.framework.FilteredBroadcastReceiver;
import net.renoseven.framework.nias.NIAService;
import net.renoseven.informationcenter.message.MessageHolder;
import net.renoseven.informationcenter.preference.ApplicationPreferences;
import net.renoseven.informationcenter.preference.MailPreferences;
import net.renoseven.informationcenter.preference.StatisticsPreferences;
import net.renoseven.informationcenter.processor.MailForwardingProcessor;
import net.renoseven.informationcenter.processor.MessageProcessor;
import net.renoseven.informationcenter.processor.SMSForwardingProcessor;
import net.renoseven.informationcenter.receiver.ApplicationStateReceiver;
import net.renoseven.informationcenter.receiver.MessageReceiver;
import net.renoseven.informationcenter.receiver.SMSReceiver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static net.renoseven.informationcenter.preference.ApplicationPreferences.CONFIG_FORWARDING_MAIL_ENABLED;
import static net.renoseven.informationcenter.preference.ApplicationPreferences.CONFIG_FORWARDING_SMS_ENABLED;
import static net.renoseven.informationcenter.preference.ApplicationPreferences.CONFIG_RECEIVER_SMS_ENABLED;

/**
 * Core Service Implementation
 * Created by RenoSeven on 2016/9/8.
 */
public class InformationService extends NIAService {

    private final Map<String, TrayPreferences> preferencesMap = new HashMap<>();
    private final Set<MessageProcessor> messageProcessors = new HashSet<>();
    private final Set<FilteredBroadcastReceiver> broadcastReceivers = new HashSet<>();
    private final int pid = android.os.Process.myPid();

    @Override
    protected void onServiceBorn() {
        // load configurations
        Log.v(TAG, "Loading settings...");
        final TrayPreferences appPref = new ApplicationPreferences(this);
        final TrayPreferences statsPref = new StatisticsPreferences(this);
        preferencesMap.put(ApplicationPreferences.MODULE_NAME, appPref);
        preferencesMap.put(StatisticsPreferences.MODULE_NAME, statsPref);
        preferencesMap.put(MailPreferences.MODULE_NAME, new MailPreferences(this));

        // init broadcastReceivers
        Log.v(TAG, "Initializing receivers...");
        broadcastReceivers.add(new MessageReceiver() {
            @Override
            protected void onMessageReceived(MessageHolder msg) {
                Log.i(TAG, "Message received");
                processMessage(msg);
            }
        });
        broadcastReceivers.add(new ApplicationStateReceiver(statsPref));

        if (appPref.getBoolean(CONFIG_RECEIVER_SMS_ENABLED, false)) {
            broadcastReceivers.add(new SMSReceiver());
        }

        // register broadcastReceivers
        Log.v(TAG, "Registering receivers...");
        for (FilteredBroadcastReceiver receiver : broadcastReceivers) {
            registerReceiver(receiver, receiver.getIntentFilter());
        }

        // register message processors
        Log.v(TAG, "Registering processors...");
        if (appPref.getBoolean(CONFIG_FORWARDING_SMS_ENABLED, false)) {
            messageProcessors.add(new SMSForwardingProcessor(this));
        }
        if (appPref.getBoolean(CONFIG_FORWARDING_MAIL_ENABLED, false)) {
            messageProcessors.add(new MailForwardingProcessor(this));
        }

        // start foreground w/ notification
        startForeground(pid, new Notification());
    }

    @Override
    protected void onServiceDead() {
        // remove notification
        stopForeground(true);

        Log.v(TAG, "Unregistering message receivers...");
        for (BroadcastReceiver receiver : broadcastReceivers) {
            unregisterReceiver(receiver);
        }
    }

    @Override
    protected Bundle onServiceUpdate(Bundle request) {
        return null;
    }

    private void processMessage(MessageHolder msg) {
        Log.i(TAG, msg.toString());
        for (MessageProcessor processor : messageProcessors) {
            processor.processMessage(preferencesMap, msg);
        }
    }
}