package net.renoseven.informationcenter.core.processor;

import android.content.Context;

import net.grandcentrix.tray.TrayPreferences;
import net.renoseven.informationcenter.core.message.MessageHolder;

import java.util.Map;

/**
 * Base Message Processor
 * Provides context & task executor
 * Created by RenoSeven on 2016/10/23.
 */

public abstract class BaseMessageProcessor implements Runnable {
    protected final String TAG = this.getClass().getSimpleName() + "@" + this.hashCode();

    protected Map<String, TrayPreferences> preferencesMap;
    protected MessageHolder message;
    protected Context context;

    public BaseMessageProcessor(Context context, Map<String, TrayPreferences> preferencesMap, MessageHolder message) {
        this.context = context;
        this.preferencesMap = preferencesMap;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            doTask();
           onTaskFinished();
        }
        catch (Exception e) {
            onTaskFailed(e);
        }
    }

    protected abstract void doTask() throws Exception;

    protected abstract void onTaskFinished();

    protected abstract void onTaskFailed(Exception e);
}