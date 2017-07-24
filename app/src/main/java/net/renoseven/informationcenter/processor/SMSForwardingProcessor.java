package net.renoseven.informationcenter.processor;

import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

import net.grandcentrix.tray.TrayPreferences;
import net.renoseven.informationcenter.preference.ApplicationPreferences;

import java.util.List;

/**
 * SMS Forwarding Processor
 * Created by RenoSeven on 2016/10/22.
 */

public class SMSForwardingProcessor extends BaseMessageProcessor {
    public final static String SMS_SENT = SMSForwardingProcessor.class.getName() + ".SMS_SENT";


    @Override
    protected void doTask() throws Exception {
        Log.i(TAG, "Forward as SMS");
        TrayPreferences appPref = preferencesMap.get(ApplicationPreferences.MODULE_NAME);
        String receiver = appPref.getString(ApplicationPreferences.CONFIG_FORWARDING_SMS_RECEIVER);
        String messageContent = message.getText();

        Log.v(TAG, "Sending SMS...");
        SmsManager smsManager = SmsManager.getDefault();
        List<String> textContents = smsManager.divideMessage(messageContent);
        for (String text : textContents) {
            smsManager.sendTextMessage(receiver, null, text, null, null);
        }
    }

    @Override
    protected void onTaskFinished() {
        Log.i(TAG, "SMS sent");
        context.sendBroadcast(new Intent(SMS_SENT));
    }

    @Override
    protected void onTaskFailed(Exception e) {
        Log.e(TAG, "SMS sending failed: " + e.getMessage());
    }
}
