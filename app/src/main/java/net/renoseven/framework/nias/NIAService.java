package net.renoseven.framework.nias;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import net.renoseven.framework.DynamicClassReceiver;

/**
 * Basic Non-Interactive Service
 * Created by RenoSeven on 2016/9/9.
 */
public abstract class NIAService extends Service implements NIAActivityListener {
    public final static String SERVICE_BORN = "SERVICE_BORN";
    public final static String SERVICE_DEAD = "SERVICE_DEAD";
    public final static String SERVICE_RESPONSE = "SERVICE_RESPONSE";
    public final static String SERVICE_REQUIRED_UPDATE = "SERVICE_REQUIRED_UPDATE";
    public final static String SERVICE_REQUIRED_STOP = "SERVICE_REQUIRED_STOP";

    protected final String TAG;
    private final String serviceClassName;
    private final DynamicClassReceiver serviceReceiver;

    public NIAService() {
        super();
        TAG = this.getClass().getSimpleName();
        serviceClassName = this.getClass().getName();
        serviceReceiver = new NIAActivityReceiver(serviceClassName, this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Starting service...");
        registerReceiver(serviceReceiver, serviceReceiver.getIntentFilter());
        onServiceBorn();
        Log.i(TAG, "Service started");
        broadcastMessage(SERVICE_BORN);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Function: onServiceBorn
     * Params: void
     * Description: triggers when service starts
     * Return: void
     */
    protected abstract void onServiceBorn();

    /**
     * Function: onServiceBorn
     * Params: void
     * Description: triggers when service dies
     * Return: void
     */
    protected abstract void onServiceDead();

    /**
     * Function: onServiceUpdate
     * Params: Bundle request (optional)
     * Description: update service & return changes as a bundle.
     * Return: Bundle reply (optional)
     */
    protected abstract
    @Nullable
    Bundle onServiceUpdate(@Nullable Bundle request);

    /**
     * Function: onRequestedUpdate
     * Params: Bundle request (optional)
     * Description: response to service update request from UI
     * Return: void
     */
    @Override
    public void onRequestedUpdate(@Nullable Bundle request) {
        Bundle reply = onServiceUpdate(request);
        Log.i(TAG, "Service updated");
        broadcastMessage(SERVICE_RESPONSE, reply);
    }

    /**
     * Function: onRequestedStop
     * Params: void
     * Description: response to service stop request from UI
     * Return: void
     */
    @Override
    public void onRequestedStop() {
        Log.d(TAG, "Stopping service...");
        onServiceDead();
        unregisterReceiver(serviceReceiver);
        Log.i(TAG, "Service stopped");
        broadcastMessage(SERVICE_DEAD);
        this.stopSelf();
    }

    /**
     * Function: broadcastMessage
     * Params: String actionName
     * Bundle bundle (optional)
     * Description: broadcast service action with additional message (optional)
     * Return: void
     */
    protected void broadcastMessage(String actionName) {
        broadcastMessage(actionName, null);
    }

    protected void broadcastMessage(String actionName, @Nullable Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(serviceClassName + '.' + actionName);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        sendBroadcast(intent);
    }

}