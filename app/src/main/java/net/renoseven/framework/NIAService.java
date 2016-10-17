package net.renoseven.framework;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Basic Non-Interactive Service
 * Created by RenoSeven on 2016/9/9.
 */
public abstract class NIAService extends Service implements NIAActivityListener {
    public final static String SERVICE_ACTION_UPDATE = ".SERVICE_ACTION_UPDATE";
    public final static String SERVICE_ACTION_SUBMIT =  ".SERVICE_ACTION_SUBMIT";
    public final static String SERVICE_ACTION_STOP = ".SERVICE_ACTION_STOP";
    public final static String SERVICE_STATE_BORN =  ".SERVICE_STATE_BORN";
    public final static String SERVICE_STATE_DEAD =  ".SERVICE_STATE_DEAD";

    protected final String TAG;
    private final String SERVICE_CLASS_NAME;
    private final DynamicClassReceiver serviceReceiver;

    public NIAService() {
        super();
        TAG = this.getClass().getSimpleName();
        SERVICE_CLASS_NAME = this.getClass().getName();
        serviceReceiver = new NIAActivityReceiver(SERVICE_CLASS_NAME, this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Starting Service...");
        super.onCreate();
        registerReceiver(serviceReceiver, serviceReceiver.getActionFilter());
        onServiceBorn();
        Log.d(TAG, "Service Started");
        broadcastMessage(SERVICE_STATE_BORN);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Stopping Service...");
        onServiceDead();
        unregisterReceiver(serviceReceiver);
        super.onDestroy();
        Log.d(TAG, "Service Stopped");
        broadcastMessage(SERVICE_STATE_DEAD);
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
     * */
    protected abstract void onServiceBorn();

    /**
     * Function: onServiceBorn
     * Params: void
     * Description: triggers when service dies
     * Return: void
     * */
    protected abstract void onServiceDead();

    /**
     * Function: onServiceUpdate
     * Params: Bundle request (optional)
     * Description: update service & return changes as a bundle.
     * Return: Bundle reply (optional)
     * */
    protected abstract @Nullable Bundle onServiceUpdate(@Nullable Bundle request);

    /**
     * Function: onRequestedUpdate
     * Params: Bundle request (optional)
     * Description: response to service update request from UI
     * Return: void
     * */
    @Override
    public void onRequestedUpdate(@Nullable Bundle request) {
        broadcastMessage(SERVICE_ACTION_SUBMIT, onServiceUpdate(request));
    }

    /**
     * Function: onRequestedStop
     * Params: void
     * Description: response to service stop request from UI
     * Return: void
     * */
    @Override
    public void onRequestedStop() {
        this.stopSelf();
    }

    /**
     * Function: broadcastMessage
     * Params: String actionName
     *         Bundle bundle (optional)
     * Description: broadcast service action with additional message (optional)
     * Return: void
     * */
    protected void broadcastMessage(String actionName) {
        broadcastMessage(actionName, null);
    }
    protected void broadcastMessage(String actionName, @Nullable Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(SERVICE_CLASS_NAME + actionName);
        if(bundle != null) {
            intent.putExtras(bundle);
        }
        sendBroadcast(intent);
    }

}