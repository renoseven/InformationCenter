package net.renoseven.informationcenter.task;

import android.content.Context;
import android.content.ContextWrapper;

/**
 * Base Task
 * provides context & task events
 * Created by RenoSeven on 2016/10/23.
 */

public abstract class BaseTask extends ContextWrapper implements Runnable {
    protected final String TAG = this.getClass().getSimpleName() + "@" + this.hashCode();

    public BaseTask(Context base) {
        super(base);
    }

    @Override
    public void run() {
        boolean result = doTask();
        if (result) {
            onTaskFinished();
        } else {
            onTaskFailed();
        }
    }

    protected abstract boolean doTask();

    protected abstract void onTaskFinished();

    protected abstract void onTaskFailed();
}
