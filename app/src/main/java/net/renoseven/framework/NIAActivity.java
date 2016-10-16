package net.renoseven.framework;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public abstract class NIAActivity extends AppCompatActivity implements NIAServiceListener {
    protected final String TAG;
    private final String SERVICE_CLASS_NAME;
    private final DynamicClassReceiver serviceStateReceiver;

    protected abstract String getServiceClassName();

    public NIAActivity() {
        TAG = this.toString();
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

    @Override
    public void onServiceSubmit(@Nullable Bundle bundle) {
        Log.d(TAG, "Service Submit");
        onServiceAlive();
    }

    protected void updateService() {
        updateService(null);
    }

    protected void updateService(@Nullable Bundle bundle) {
        Log.i(this.toString(), "Request Service Update");
        broadcastMessage(NIAService.SERVICE_ACTION_UPDATE, bundle);
    }

    protected void stopService() {
        Log.i(this.toString(), "Request Service Stop");
        broadcastMessage(NIAService.SERVICE_ACTION_STOP);
    }

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
