package net.renoseven.informationcenter.processor;

import android.content.Context;
import android.util.Log;

import net.grandcentrix.tray.TrayPreferences;
import net.grandcentrix.tray.core.ItemNotFoundException;
import net.renoseven.informationcenter.message.MessageHolder;
import net.renoseven.informationcenter.message.MessageType;
import net.renoseven.informationcenter.preference.ApplicationPreferences;
import net.renoseven.informationcenter.task.SMSSendingTask;

import java.util.Map;

/**
 * SMS Forwarding Processor
 * Created by RenoSeven on 2016/10/22.
 */

public class SMSForwardingProcessor extends BaseMessageProcessor implements MessageProcessor {

    public SMSForwardingProcessor(Context base) {
        super(base);
    }

    @Override
    public void processMessage(final Map<String, TrayPreferences> preferencesMap, MessageHolder message) {
        final TrayPreferences appPref = preferencesMap.get(ApplicationPreferences.MODULE_NAME);

        Log.i(TAG, "Forward as SMS");
        try {
            String receiver = appPref.getString(ApplicationPreferences.CONFIG_FORWARDING_SMS_RECEIVER);
            MessageHolder sms = new MessageHolder(MessageType.SMS);
            sms.setTimeStamp(message.getTimeStamp());
            sms.setText(message.getText());
            sms.setReceiver(receiver);

            Log.d(TAG, "Starting job...");
            this.runTask(new SMSSendingTask(getBaseContext(), sms));
        } catch (ItemNotFoundException e) {
            Log.e(TAG, "Receiver is not set");
        }
    }
}
