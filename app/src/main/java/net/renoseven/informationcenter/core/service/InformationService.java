package net.renoseven.informationcenter.core.service;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.util.Log;

import net.grandcentrix.tray.TrayPreferences;
import net.renoseven.framework.FilteredBroadcastReceiver;
import net.renoseven.framework.nias.NIAService;
import net.renoseven.informationcenter.core.bean.Message;
import net.renoseven.informationcenter.core.receiver.ApplicationStateReceiver;
import net.renoseven.informationcenter.core.receiver.MessageReceiver;
import net.renoseven.informationcenter.module.mailforwarding.MailForwardingModule;
import net.renoseven.informationcenter.module.smsforwarding.SMSForwardingModule;
import net.renoseven.informationcenter.module.smsmonitor.SMSMonitorModule;
import net.renoseven.informationcenter.preference.ApplicationPreferences;
import net.renoseven.informationcenter.preference.MailPreferences;
import net.renoseven.informationcenter.preference.StatisticsPreferences;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static net.renoseven.informationcenter.preference.ApplicationPreferences.CONFIG_FORWARDING_MAIL_ENABLED;
import static net.renoseven.informationcenter.preference.ApplicationPreferences.CONFIG_FORWARDING_SMS_ENABLED;
import static net.renoseven.informationcenter.preference.ApplicationPreferences.CONFIG_RECEIVER_SMS_ENABLED;

/**
 * Core Service Implementation
 * Created by RenoSeven on 2016/9/8.
 */
public class InformationService extends NIAService {

    private final static ExecutorService executor = Executors.newCachedThreadPool();
    private final Map<String, TrayPreferences> preferences = new HashMap<>();
    private final Set<ServiceModule> serviceModules = new HashSet<>();
    private final Set<FilteredBroadcastReceiver> broadcastReceivers = new HashSet<>();
    private final int pid = android.os.Process.myPid();

    @Override
    protected void onServiceBorn() {
        // load configurations
        Log.v(TAG, "Loading settings...");
        final TrayPreferences appPref = new ApplicationPreferences(this);
        final TrayPreferences statsPref = new StatisticsPreferences(this);
        preferences.put(ApplicationPreferences.MODULE_NAME, appPref);
        preferences.put(StatisticsPreferences.MODULE_NAME, statsPref);
        preferences.put(MailPreferences.MODULE_NAME, new MailPreferences(this));

        Log.d(TAG, "Initializing system receivers...");
        broadcastReceivers.add(new MessageReceiver() {
            @Override
            protected void onMessageReceived(Message msg) {
                Log.i(TAG, "Message received");
                processMessage(msg);
            }
        });
        broadcastReceivers.add(new ApplicationStateReceiver(statsPref));

        Log.d(TAG, "Registering modules...");
        if (appPref.getBoolean(CONFIG_RECEIVER_SMS_ENABLED, false)) {
            Log.v(TAG, "CONFIG_RECEIVER_SMS_ENABLED = TRUE");
            serviceModules.add(new SMSMonitorModule());
        }
        if (appPref.getBoolean(CONFIG_FORWARDING_SMS_ENABLED, false)) {
            Log.v(TAG, "CONFIG_FORWARDING_SMS_ENABLED = TRUE");
            serviceModules.add(new SMSForwardingModule());
        }
        if (appPref.getBoolean(CONFIG_FORWARDING_MAIL_ENABLED, false)) {
            Log.v(TAG, "CONFIG_FORWARDING_MAIL_ENABLED = TRUE");
            serviceModules.add(new MailForwardingModule());
        }

        Log.d(TAG, "Initializing modules...");
        for (ServiceModule module : serviceModules) {
            Log.v(TAG, module.getModuleName());
            broadcastReceivers.addAll(module.getReceivers());
        }

        Log.d(TAG, "Registering receivers...");
        for (FilteredBroadcastReceiver receiver : broadcastReceivers) {
            Log.v(TAG, receiver.getClass().getName());
            registerReceiver(receiver, receiver.getIntentFilter(), receiver.getPermission(), receiver.getHandler());
        }
        Log.d(TAG, "Receiver number = " + broadcastReceivers.size());

        // start foreground w/ notification
        startForeground(pid, new Notification());

    }

    @Override
    protected void onServiceDead() {
        // remove notification
        stopForeground(true);

        Log.v(TAG, "Unregister message receivers...");
        for (BroadcastReceiver receiver : broadcastReceivers) {
            unregisterReceiver(receiver);
        }
    }

    @Override
    protected Bundle onServiceUpdate(Bundle request) {
        return null;
    }

    private void processMessage(Message message) {
        Log.i(TAG, message.toString());
        for (ServiceModule module : serviceModules) {
            Set<? extends Runnable> processors = module.getProcessors(this, preferences, message);
            for (Runnable processor : processors) {
                executor.execute(processor);
            }
        }
    }
}