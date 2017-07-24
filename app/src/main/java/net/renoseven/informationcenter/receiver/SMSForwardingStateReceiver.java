package net.renoseven.informationcenter.receiver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;

import net.renoseven.framework.FilteredBroadcastReceiver;
import net.renoseven.util.ToastUtil;

/**
 * SMS Forwarding State Receiver
 * Created by RenoSeven on 2017/7/25.
 */

public class SMSForwardingStateReceiver extends FilteredBroadcastReceiver {
    public final static String SMS_SENDING_RESULT = SMSForwardingStateReceiver.class.getName() + ".SMS_SENDING_RESULT";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_SENDING_RESULT)) {
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
    public IntentFilter getIntentFilter() {
        return new IntentFilter(SMS_SENDING_RESULT);
    }

}
