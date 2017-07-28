package net.renoseven.framework;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.annotation.NonNull;

import static android.content.IntentFilter.SYSTEM_HIGH_PRIORITY;
import static android.content.IntentFilter.SYSTEM_LOW_PRIORITY;

/**
 * Filtered Broadcast Receiver
 * Provides a way to return the filter that receiver uses
 * Created by RenoSeven on 2016/10/22.
 */

public abstract class FilteredBroadcastReceiver extends BroadcastReceiver {
    protected static int FILTER_PRIORITY_MAX = SYSTEM_HIGH_PRIORITY - 1;
    protected static int FILTER_PRIORITY_MIN = SYSTEM_LOW_PRIORITY + 1;
    protected final String TAG = this.getClass().getSimpleName();

    @NonNull
    public abstract IntentFilter getIntentFilter();

    public abstract String getPermission();

    public abstract Handler getHandler();
}
