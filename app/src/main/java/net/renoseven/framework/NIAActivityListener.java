package net.renoseven.framework;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * NIAService Action Listener
 * Created by RenoSeven on 2016/9/10.
 */
interface NIAActivityListener {

    /**
     * Function: onRequestedStop
     * Params: void
     * Description: response to service stop request from UI
     * Return: void
     * */
    void onRequestedStop();

    /**
     * Function: onRequestedUpdate
     * Params: Bundle request (optional)
     * Description: response to service update request from UI
     * Return: void
     * */
    void onRequestedUpdate(@Nullable Bundle request);
}
