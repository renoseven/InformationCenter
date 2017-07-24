package net.renoseven.informationcenter.processor;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.preference.Preference;

import net.grandcentrix.tray.TrayPreferences;
import net.renoseven.informationcenter.message.MessageHolder;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    public void init(Context context, Map<String, TrayPreferences> preferencesMap, MessageHolder message) {
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