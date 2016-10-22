package net.renoseven.informationcenter.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
    protected static String TAG = SMSReceiver.class.getSimpleName();
    public final static String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Log.d(TAG, "Incoming SMS");
            processSMS(context, intent);
        }
    }

    private void processSMS(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");

        SmsMessage[] messages = new SmsMessage[pdus.length];

        for (int i = 0; i < pdus.length; i++) {
            messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
        }

        long timeStamp = messages[0].getTimestampMillis();
        String sender = messages[0].getOriginatingAddress();
        StringBuilder content = new StringBuilder();
        for (SmsMessage msg : messages) {
            content.append(msg.getMessageBody());
        }

        MessageHolder msg = new MessageHolder(MessageType.SMS, timeStamp, "utf-8", sender, null, null, null, null, content.toString());

        Intent msgIntent = new Intent();
        msgIntent.setAction(MessageReceiver.MESSAGE_RECEIVED);
        msgIntent.putExtra(MessageReceiver.MESSAGE_CONTENT, msg);
        context.sendBroadcast(msgIntent);
    }

    @NonNull
    @Override
    public IntentFilter getIntentFilter() {
        return new IntentFilter(SMS_RECEIVED);
    }
}