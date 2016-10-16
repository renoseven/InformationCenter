package net.renoseven.framework;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * NIAService State Listener
 * Created by RenoSeven on 2016/9/10.
 */
public interface NIAServiceListener {
    void onServiceBorn();
    void onServiceDead();
    void onServiceAlive();
    void onServiceSubmit(@Nullable Bundle bundle);
}
