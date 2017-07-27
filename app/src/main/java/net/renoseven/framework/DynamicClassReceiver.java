package net.renoseven.framework;

/**
 * Dynamic Class Receiver
 * Created by RenoSeven on 2016/9/9.
 */
public abstract class DynamicClassReceiver extends FilteredBroadcastReceiver {
    private final String serviceClassName;

    public DynamicClassReceiver(String serviceClassName) {
        this.serviceClassName = serviceClassName;
    }

    public String getFullActionName(String actionName) {
        return serviceClassName + '.' + actionName;
    }
}
