package net.renoseven.informationcenter.processor;

import android.content.Context;
import android.content.ContextWrapper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Base Message Processor
 * Provides context & task executor
 * Created by RenoSeven on 2016/10/23.
 */

public abstract class BaseMessageProcessor extends ContextWrapper implements MessageProcessor {
    private final static ExecutorService executor = Executors.newCachedThreadPool();

    protected final String TAG;

    public BaseMessageProcessor(Context base) {
        super(base);
        TAG = this.getClass().getSimpleName();
    }

    protected void runTask(Runnable task) {
        executor.execute(task);
    }
}
