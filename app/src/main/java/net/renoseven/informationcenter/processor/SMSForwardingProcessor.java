package net.renoseven.informationcenter.processor;

import android.util.Log;

import net.grandcentrix.tray.TrayPreferences;
import net.renoseven.informationcenter.message.MessageHolder;
import net.renoseven.informationcenter.message.MessageType;
import net.renoseven.informationcenter.preference.ApplicationPreferences;
import net.renoseven.informationcenter.task.SMSSendingTask;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Singleton SMS Forwarding Processor
 * Created by RenoSeven on 2016/10/22.
 */

public class SMSForwardingProcessor implements MessageProcessor {
    private final static String TAG = SMSForwardingProcessor.class.getSimpleName();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private static class SingletonHolder {
        private static final MessageProcessor INSTANCE = new SMSForwardingProcessor();
    }

    private SMSForwardingProcessor() {
    }

    public static MessageProcessor getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void processMessage(final Map<String, TrayPreferences> preferencesMap, MessageHolder message) {
        final TrayPreferences appPref = preferencesMap.get(ApplicationPreferences.MODULE_NAME);

        Log.i(TAG, "Forward as SMS");
        String receiver = appPref.getString(ApplicationPreferences.CONFIG_FORWARDING_SMS_RECEIVER, null);
        if (receiver != null && receiver.isEmpty()) {
            Log.e(TAG, "Receiver is not set");
        }
        else {
            MessageHolder sms = new MessageHolder(MessageType.SMS);
            sms.setTimeStamp(message.getTimeStamp());
            sms.setText(message.getText());
            sms.setReceiver(receiver);
            Log.d(TAG, "Starting job...");
            executorService.execute(new SMSSendingTask(sms));
        }
    }
}
