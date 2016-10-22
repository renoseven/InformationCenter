package net.renoseven.framework;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Service State Receiver (Used in UI)
 * Created by RenoSeven on 2016/9/9.
 */
public class NIAServiceReceiver extends DynamicClassReceiver {
    private final NIAServiceListener stateListener;

    public NIAServiceReceiver(String serviceClassName, NIAServiceListener stateListener) {
        super(serviceClassName);
        this.stateListener = stateListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String actionName = intent.getAction();

        if (actionName.equals(getFullActionName(NIAService.SERVICE_ACTION_SUBMIT))) {
            Log.d(TAG, "Received service submit");
            // read data from bundles
            stateListener.onServiceSubmit(intent.getExtras());
        } else if (actionName.equals(getFullActionName(NIAService.SERVICE_STATE_BORN))) {
            stateListener.onServiceBorn();
        } else if (actionName.equals(getFullActionName(NIAService.SERVICE_STATE_DEAD))) {
            stateListener.onServiceDead();
        }
    }

    @NonNull
    @Override
    public IntentFilter getIntentFilter() {
        IntentFilter actionFilter = new IntentFilter();
        actionFilter.addAction(getFullActionName(NIAService.SERVICE_ACTION_SUBMIT));
        actionFilter.addAction(getFullActionName(NIAService.SERVICE_STATE_BORN));
        actionFilter.addAction(getFullActionName(NIAService.SERVICE_STATE_DEAD));
        return actionFilter;
    }
}
