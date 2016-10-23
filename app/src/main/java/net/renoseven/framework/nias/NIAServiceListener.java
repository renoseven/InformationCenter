package net.renoseven.framework.nias;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * NIAService State Listener
 * Created by RenoSeven on 2016/9/10.
 */
interface NIAServiceListener {

    /**
     * Function: onServiceBorn
     * Params: void
     * Description: triggers when service starts
     * Return: void
     */
    void onServiceBorn();

    /**
     * Function: onServiceDead
     * Params: void
     * Description: triggers when service dies
     * Return: void
     */
    void onServiceDead();

    /**
     * Function: onServiceSubmit
     * Params: Bundle reply (optional)
     * Description: triggers when a service submits data.
     * Return: void
     */
    void onServiceSubmit(@Nullable Bundle reply);
}
