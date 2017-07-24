package net.renoseven.informationcenter.processor;

import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

import net.grandcentrix.tray.TrayPreferences;
import net.renoseven.informationcenter.preference.ApplicationPreferences;

import java.util.List;

import static net.renoseven.informationcenter.receiver.SMSForwardingStateReceiver.SMS_SENDING_RESULT;

/**
 * SMS Forwarding Processor
 * Created by RenoSeven on 2016/10/22.
 */

public class SMSForwardingProcessor extends BaseMessageProcessor {

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
            PendingIntent messageState = PendingIntent.getBroadcast(context, 0, new Intent(SMS_SENDING_RESULT), PendingIntent.FLAG_ONE_SHOT);
            smsManager.sendTextMessage(receiver, null, text, messageState, null);
        }
    }

    @Override
    protected void onTaskFinished() {
        Log.d(TAG, "SMS sending task finished");
    }

    @Override
    protected void onTaskFailed(Exception e) {
        Log.e(TAG, "SMS sending task failed: " + e.getMessage());
    }
}
