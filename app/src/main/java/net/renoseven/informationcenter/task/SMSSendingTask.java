package net.renoseven.informationcenter.task;

import android.telephony.SmsManager;
import android.util.Log;

import net.renoseven.informationcenter.message.MessageHolder;

import java.util.List;

/**
 * Task of sending a sms
 * Created by RenoSeven on 2016/9/9.
 */
public class SMSSendingTask implements Runnable {
    public final static String SMS_SENT = "android.provider.Telephony.SMS_SENT";
    private final String TAG = this.getClass().getSimpleName() + "@" + this.hashCode();

    private MessageHolder message;

    public SMSSendingTask(MessageHolder message) {
        this.message = message;
        Log.i(TAG, message.toString());
    }

    @Override
    public void run() {
        Log.v(TAG, "Sending SMS...");
        SmsManager smsManager = SmsManager.getDefault();
        List<String> textContents = smsManager.divideMessage(message.getText());
        for (String text : textContents) {
            smsManager.sendTextMessage(message.getReceiver(), null, text, null, null);
        }
        Log.i(TAG, "SMS sent");
    }
}