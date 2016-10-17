package net.renoseven.framework;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;

/**
 * Dynamic Class Receiver
 * Created by RenoSeven on 2016/9/9.
 */
public abstract class DynamicClassReceiver extends BroadcastReceiver {
    protected final String TAG;
    private final String SERVICE_CLASS_NAME;

    public DynamicClassReceiver(String serviceClassName) {
        TAG = this.getClass().getSimpleName();
        SERVICE_CLASS_NAME = serviceClassName;
    }

    public String getFullActionName(String actionName) {
        return SERVICE_CLASS_NAME + actionName;
    }

    public abstract IntentFilter getActionFilter();
}
