package net.renoseven.framework;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.support.annotation.NonNull;

/**
 * Filtered Broadcast Receiver
 * Provides a way to return the filter that receiver uses
 * Created by RenoSeven on 2016/10/22.
 */

public abstract class FilteredBroadcastReceiver extends BroadcastReceiver {
    protected static int FILTER_PRIORITY_MAX = 999;
    protected static int FILTER_PRIORITY_MIN = -999;
    protected final String TAG = this.getClass().getSimpleName();

    @NonNull
    public abstract IntentFilter getIntentFilter();
}
