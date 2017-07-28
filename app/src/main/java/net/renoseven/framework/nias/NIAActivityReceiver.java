package net.renoseven.framework.nias;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import net.renoseven.framework.DynamicClassReceiver;

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
        if (actionName.equals(getFullActionName(NIAService.SERVICE_REQUIRED_UPDATE))) {
            Log.d(TAG, "Received service update request");
            actionListener.onRequestedUpdate(intent.getExtras());
        } else if (actionName.equals(getFullActionName(NIAService.SERVICE_REQUIRED_STOP))) {
            Log.d(TAG, "Received service stop request");
            actionListener.onRequestedStop();
        }
    }

    @NonNull
    @Override
    public IntentFilter getIntentFilter() {
        IntentFilter actionFilter = new IntentFilter();
        actionFilter.addAction(getFullActionName(NIAService.SERVICE_REQUIRED_UPDATE));
        actionFilter.addAction(getFullActionName(NIAService.SERVICE_REQUIRED_STOP));
        return actionFilter;
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public Handler getHandler() {
        return null;
    }
}
