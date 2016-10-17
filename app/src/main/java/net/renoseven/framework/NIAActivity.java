package net.renoseven.framework;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public abstract class NIAActivity extends AppCompatActivity implements NIAServiceController {
    protected final String TAG;
    private final String SERVICE_CLASS_NAME;
    private final DynamicClassReceiver serviceStateReceiver;

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
        updateService();
    }

    /**
     * Function: getServiceClassName
     * Params: void
     * Description: triggers when a service submits data.
     * Return: String
     * */
    protected abstract String getServiceClassName();

    /**
     * Function: onServiceSubmit
     * Params: Bundle reply (optional)
     * Description: triggers when a service submits data.
     * Return: void
     * */
    @Override
    public void onServiceSubmit(@Nullable Bundle reply) {
        Log.d(TAG, "Service Submit");
        onServiceAlive();
    }

    /**
     * Function: updateService
     * Params: Bundle request (optional)
     * Description: send a service update request
     * Return: void
     * */
    public void updateService() {
        updateService(null);
    }
    public void updateService(@Nullable Bundle request) {
        Log.i(TAG, "Request Service Update");
        broadcastMessage(NIAService.SERVICE_ACTION_UPDATE, request);
    }

    /**
     * Function: stopService
     * Params: void
     * Description: send a service stop request
     * Return: void
     * */
    protected void stopService() {
        Log.i(TAG, "Request Service Stop");
        broadcastMessage(NIAService.SERVICE_ACTION_STOP);
    }

    /**
     * Function: broadcastMessage
     * Params: String actionName
     *         Bundle bundle (optional)
     * Description: broadcast service state
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
