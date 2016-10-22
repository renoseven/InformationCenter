package net.renoseven.framework;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Service Controller (Used in Service)
 * Created by RenoSeven on 2016/9/9.
 */
public class NIAActivityReceiver extends DynamicClassReceiver {
    private final NIAActivityListener actionListener;

    public NIAActivityReceiver(String serviceClassName, NIAActivityListener actionListener) {
        super(serviceClassName);
        this.actionListener = actionListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String actionName = intent.getAction();
        if (actionName.equals(getFullActionName(NIAService.SERVICE_ACTION_UPDATE))) {
            Log.d(TAG, "Received service update request");
            actionListener.onRequestedUpdate(intent.getExtras());
        } else if (actionName.equals(getFullActionName(NIAService.SERVICE_ACTION_STOP))) {
            Log.d(TAG, "Received service stop request");
            actionListener.onRequestedStop();
        }
    }

    @NonNull
    @Override
    public IntentFilter getIntentFilter() {
        IntentFilter actionFilter = new IntentFilter();
        actionFilter.addAction(getFullActionName(NIAService.SERVICE_ACTION_UPDATE));
        actionFilter.addAction(getFullActionName(NIAService.SERVICE_ACTION_STOP));
        return actionFilter;
    }
}
