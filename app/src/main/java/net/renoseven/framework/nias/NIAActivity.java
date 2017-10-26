package net.renoseven.framework.nias;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import net.renoseven.framework.DynamicClassReceiver;
import net.renoseven.framework.ExtendedActivity;

/**
 * Basic Non-Interactive Service Activity
 * Created by RenoSeven on 2016/9/9.
 */
public abstract class NIAActivity extends ExtendedActivity implements NIAServiceListener {
    private final static String META_KEY_BIND_SERVICE = "service";
    protected final String TAG;
    private String serviceClassName;
    private DynamicClassReceiver serviceStateReceiver;
    private boolean isServiceRunning;

    public NIAActivity() {
        TAG = this.getClass().getSimpleName();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        String packageName = getPackageName();
        serviceClassName = getMetaValue(META_KEY_BIND_SERVICE);
        // complete package name
        if (!serviceClassName.startsWith(packageName)) {
            serviceClassName = packageName + serviceClassName;
        }
        serviceStateReceiver = new NIAServiceReceiver(serviceClassName, this);
        Log.d(TAG, "service: " + serviceClassName);

        // bind service
        registerReceiver(serviceStateReceiver, serviceStateReceiver.getIntentFilter());
    }

    @Override
    protected void onDestroy() {
        // unbind service
        unregisterReceiver(serviceStateReceiver);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        // first start
        if (!isServiceRunning) {
            updateUI(null);
        }
        // try updating service to check if it is running
        updateService();
    }

    @Override
    public void onServiceBorn() {
        Log.i(TAG, "Service started");
        isServiceRunning = true;
        updateUI(null);
    }

    @Override
    public void onServiceDead() {
        Log.i(TAG, "Service stopped");
        isServiceRunning = false;
        updateUI(null);
    }

    @Override
    public void onServiceUpdate(@Nullable Bundle bundle) {
        Log.i(TAG, "Service updated");
        isServiceRunning = true;
        updateUI(bundle);
    }

    /**
     * Function: updateUI
     * Params: Bundle bundle ()
     * Description: update UI by additional data (optional).
     * Return: void
     */
    protected abstract void updateUI(@Nullable Bundle bundle);

    /**
     * Function: isServiceAlive
     * Params: void
     * Description: show if service is alive
     * Return: boolean
     */
    protected boolean isServiceAlive() {
        return isServiceRunning;
    }

    /**
     * Function: updateService
     * Params: Bundle request (optional)
     * Description: send a service update request
     * Return: void
     */
    protected void updateService() {
        updateService(null);
    }

    protected void updateService(@Nullable Bundle request) {
        Log.d(TAG, "Request service update");
        broadcastMessage(NIAService.SERVICE_REQUESTED_UPDATE, request);
    }

    /**
     * Function: startService
     * Params: void
     * Description: start tagged service
     * Return: void
     */
    protected void startService() {
        Intent startIntent = null;
        try {
            startIntent = new Intent(this, Class.forName(serviceClassName));
        } catch (ClassNotFoundException e) {
            Log.d(TAG, e.toString());
        }
        super.startService(startIntent);
    }

    /**
     * Function: stopService
     * Params: void
     * Description: send a service stop request
     * Return: void
     */
    protected void stopService() {
        Log.d(TAG, "Request service stop");
        broadcastMessage(NIAService.SERVICE_REQUESTED_STOP);
    }

    /**
     * Function: broadcastMessage
     * Params: String actionName
     * Bundle bundle (optional)
     * Description: broadcast service state
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
