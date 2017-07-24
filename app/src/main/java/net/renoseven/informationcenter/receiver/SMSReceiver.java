package net.renoseven.informationcenter.receiver;

import android.app.Activity;
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
import net.renoseven.util.ToastUtil;

import static net.renoseven.informationcenter.processor.SMSForwardingProcessor.SMS_SENDING_RESULT;

/**
 * SMS Receiver
 * Created by RenoSeven on 2016/9/8.
 */

public class SMSReceiver extends FilteredBroadcastReceiver {
    protected static String TAG = SMSReceiver.class.getSimpleName();
    public final static String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();
        if (intentAction.equals(SMS_RECEIVED)) {
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
        else if(intentAction.equals(SMS_SENDING_RESULT)) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    ToastUtil.showToast(context, "SMS Sent");
                    break;
                default:
                    ToastUtil.showToast(context, "SMS Sending failed");
                    break;
            }
        }
    }

    @NonNull
    @Override
    public IntentFilter getIntentFilter()
    {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SMS_RECEIVED);
        intentFilter.addAction(SMS_SENDING_RESULT);
        return intentFilter;
    }
}