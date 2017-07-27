package net.renoseven.informationcenter.module.smsmonitor;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.telephony.SmsMessage;
import android.util.Log;

import net.renoseven.framework.FilteredBroadcastReceiver;
import net.renoseven.informationcenter.core.message.Message;
import net.renoseven.informationcenter.core.receiver.MessageReceiver;

/**
 * SMS Receiver
 * Created by RenoSeven on 2016/9/8.
 */

public class SMSMonitor extends FilteredBroadcastReceiver {
    public final static String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    protected static String TAG = SMSMonitor.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, intent.toString());
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Log.d(TAG, "Incoming SMS");
            SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

            StringBuilder content = new StringBuilder();
            for (SmsMessage msg : messages) {
                content.append(msg.getDisplayMessageBody());
            }

            Message msg = new Message();
            msg.setTimeStamp(messages[0].getTimestampMillis());
            msg.setSender(messages[0].getOriginatingAddress());
            msg.setCharset("utf-8");
            msg.setText(content.toString());

            Intent msgIntent = new Intent();
            msgIntent.setAction(MessageReceiver.MESSAGE_RECEIVED);
            msgIntent.putExtra(MessageReceiver.MESSAGE_CONTENT, msg);
            context.sendBroadcast(msgIntent);
        }
    }

    @NonNull
    @Override
    public IntentFilter getIntentFilter()
    {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.setPriority(FILTER_PRIORITY_MAX);
        intentFilter.addAction(SMS_RECEIVED);
        return intentFilter;
    }
}