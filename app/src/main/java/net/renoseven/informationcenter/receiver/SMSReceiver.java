package net.renoseven.informationcenter.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.telephony.SmsMessage;
import android.util.Log;

import net.renoseven.framework.FilteredBroadcastReceiver;
import net.renoseven.informationcenter.message.MessageHolder;
import net.renoseven.informationcenter.message.MessageType;

/**
 * SMS Receiver
 * Created by RenoSeven on 2016/9/8.
 */

public class SMSReceiver extends FilteredBroadcastReceiver {
    public final static String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    protected static String TAG = SMSReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Log.d(TAG, "Incoming SMS");
            SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

            StringBuilder content = new StringBuilder();
            for (SmsMessage msg : messages) {
                content.append(msg.getDisplayMessageBody());
            }

            MessageHolder msg = new MessageHolder(MessageType.SMS);
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
        return new IntentFilter(SMS_RECEIVED);
    }
}