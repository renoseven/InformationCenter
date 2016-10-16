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
    protected final String TAG = this.toString();

    private MessageHolder message;

    public SMSSendingTask(MessageHolder message) {
        this.message = message;
        Log.i(TAG, message.toString());
    }

    @Override
    public void run() {
        //TODO: show sms sending status
        Log.d(TAG, "Sending SMS...");
        SmsManager smsManager = SmsManager.getDefault();
        List<String> textContents = smsManager.divideMessage(message.getText());
        for (String text : textContents) {
            smsManager.sendTextMessage(message.getReceiver(), null, text, null, null);
        }
    }
}