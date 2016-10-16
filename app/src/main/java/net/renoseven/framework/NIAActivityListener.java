package net.renoseven.framework;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * NIAService Action Listener
 * Created by RenoSeven on 2016/9/10.
 */
public interface NIAActivityListener {
    void onRequestedUpdate(@Nullable Bundle request);
    void onRequestedStop();
}
