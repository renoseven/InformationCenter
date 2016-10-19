package net.renoseven.framework;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Basic Non-Interactive Service Activity
 * Created by RenoSeven on 2016/9/9.
 */
public abstract class NIAActivity extends AppCompatActivity implements NIAServiceListener {
    protected final String TAG;
    private final String SERVICE_CLASS_NAME;
    private final DynamicClassReceiver serviceStateReceiver;
    private boolean isServiceRunning;

    public NIAActivity() {
        TAG = this.getClass().getSimpleName();
        SERVICE_CLASS_NAME = getServiceClassName();
        serviceStateReceiver = new NIAServiceReceiver(SERVICE_CLASS_NAME, this);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        registerReceiver(serviceStateReceiver, serviceStateReceiver.getActionFilter());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(serviceStateReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isServiceRunning) {
            updateService();
        }
        updateUI(null);
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
    public void onServiceSubmit(@Nullable Bundle reply) {
        Log.i(TAG, "Service updated");
        updateUI(reply);
    }

    /**
     * Function: getServiceClassName
     * Params: void
     * Description: triggers when a service submits data.
     * Return: String
     */
    protected abstract String getServiceClassName();

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
        broadcastMessage(NIAService.SERVICE_ACTION_UPDATE, request);
    }

    /**
     * Function: stopService
     * Params: void
     * Description: send a service stop request
     * Return: void
     */
    protected void stopService() {
        Log.d(TAG, "Request service stop");
        broadcastMessage(NIAService.SERVICE_ACTION_STOP);
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
        intent.setAction(SERVICE_CLASS_NAME + actionName);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        sendBroadcast(intent);
    }
}
