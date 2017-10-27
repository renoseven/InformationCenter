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
import net.renoseven.informationcenter.preference.ApplicationPreferences;
import net.renoseven.informationcenter.preference.MailPreferences;
import net.renoseven.informationcenter.preference.StatisticsPreferences;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        Log.d(TAG, "Loading settings...");
        final String[] modules = this.getMetaValue("modules").split(";");
        final TrayPreferences appPref = new ApplicationPreferences(this);
        final TrayPreferences statsPref = new StatisticsPreferences(this);
        final TrayPreferences mailPref =  new MailPreferences(this);
        preferences.put(ApplicationPreferences.DB_NAME, appPref);
        preferences.put(StatisticsPreferences.DB_NAME, statsPref);
        preferences.put(MailPreferences.DB_NAME, mailPref);

        Log.d(TAG, "Initializing system receivers...");
        broadcastReceivers.add(new MessageReceiver() {
            @Override
            protected void onMessageReceived(Message msg) {
                Log.i(TAG, "Message received");
                processMessage(msg);
            }
        });
        broadcastReceivers.add(new ApplicationStateReceiver(statsPref));

        Log.d(TAG, "Initializing modules...");
        for(String moduleName : modules) {
            moduleName = moduleName.trim();
            String fullModuleName = getPackageName() + moduleName;
            Log.v(TAG, fullModuleName);
            // 'app' is the prefix of database name
            if(appPref.getBoolean( "app" + moduleName, false)) {
                try {
                    ServiceModule serviceModule = (ServiceModule) Class.forName(fullModuleName).newInstance();
                    serviceModules.add(serviceModule);
                } catch (Exception e) {
                    Log.e(TAG, "Cannot initialize " + fullModuleName);
                }
            }
        }

        Log.d(TAG, "Registering modules...");
        for (ServiceModule module : serviceModules) {
            Log.v(TAG, module.getModuleName());
            broadcastReceivers.addAll(module.getReceivers());
        }

        Log.d(TAG, "Registering receivers...");
        for (FilteredBroadcastReceiver receiver : broadcastReceivers) {
            Log.v(TAG, receiver.getClass().getName());
            registerReceiver(receiver, receiver.getIntentFilter(), receiver.getPermission(), null);
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
    protected Bundle onServiceUpdateRequested(Bundle request) {
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