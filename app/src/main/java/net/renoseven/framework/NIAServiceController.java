package net.renoseven.framework;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * NIAService State Listener
 * Created by RenoSeven on 2016/9/10.
 */
interface NIAServiceController {

    /**
     * Function: onServiceBorn
     * Params: void
     * Description: triggers when service starts
     * Return: void
     * */
    void onServiceBorn();

    /**
     * Function: onServiceDead
     * Params: void
     * Description: triggers when service dies
     * Return: void
     * */
    void onServiceDead();

    /**
     * Function: onServiceAlive
     * Params: void
     * Description: triggers when service updates
     * Return: void
     * */
    void onServiceAlive();

    /**
     * Function: updateService
     * Params: Bundle request (optional)
     * Description: send a service update request
     * Return: void
     * */
    void updateService(@Nullable Bundle request);

    /**
     * Function: onServiceSubmit
     * Params: Bundle reply (optional)
     * Description: triggers when a service submits data.
     * Return: void
     * */
    void onServiceSubmit(@Nullable Bundle reply);
}
