package net.renoseven.informationcenter.task;

import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

import net.renoseven.informationcenter.message.MessageHolder;

import java.util.List;

/**
 * Task of sending a sms
 * Created by RenoSeven on 2016/9/9.
 */
public class SMSSendingTask extends BaseTask {
    public final static String SMS_SENT = SMSSendingTask.class.getName() + ".SMS_SENT";

    private MessageHolder message;

    public SMSSendingTask(final Context base, final MessageHolder message) {
        super(base);
        this.message = message;
        Log.i(TAG, message.toString());
    }

    @Override
    public boolean doTask() {
        Log.v(TAG, "Sending SMS...");
        SmsManager smsManager = SmsManager.getDefault();
        List<String> textContents = smsManager.divideMessage(message.getText());
        for (String text : textContents) {
            smsManager.sendTextMessage(message.getReceiver(), null, text, null, null);
        }
        return true;
    }

    @Override
    protected void onTaskFinished() {
        Log.i(TAG, "SMS sent");
        sendBroadcast(new Intent(SMS_SENT));
    }

    @Override
    protected void onTaskFailed() {
        Log.e(TAG, "Mail sending failed");
    }
}