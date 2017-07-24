package net.renoseven.informationcenter.processor;

import android.app.PendingIntent;
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
    public final static String SMS_SENDING_RESULT = SMSForwardingProcessor.class.getName() + ".SMS_SENDING_RESULT";

    private static int messageID = 0;

    @Override
    protected synchronized void doTask() throws Exception {
        Log.i(TAG, "Forward as SMS");
        TrayPreferences appPref = preferencesMap.get(ApplicationPreferences.MODULE_NAME);
        String receiver = appPref.getString(ApplicationPreferences.CONFIG_FORWARDING_SMS_RECEIVER);
        String messageContent = message.getText();

        Log.v(TAG, "Sending SMS...");
        SmsManager smsManager = SmsManager.getDefault();
        List<String> textContents = smsManager.divideMessage(messageContent);

        for (String text : textContents) {
            PendingIntent messageState = PendingIntent.getBroadcast(context, messageID, new Intent(SMS_SENDING_RESULT), PendingIntent.FLAG_ONE_SHOT);
            smsManager.sendTextMessage(receiver, null, text, messageState, null);
            messageID ++;
        }
    }

    @Override
    protected void onTaskFinished() {
        Log.i(TAG, "SMS sent");
    }

    @Override
    protected void onTaskFailed(Exception e) {
        Log.e(TAG, "SMS sending failed: " + e.getMessage());
    }
}
