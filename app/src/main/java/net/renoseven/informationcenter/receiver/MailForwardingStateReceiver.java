package net.renoseven.informationcenter.receiver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;

import net.renoseven.framework.FilteredBroadcastReceiver;
import net.renoseven.util.ToastUtil;

/**
 * Mail Forwarding State Receiver
 * Created by RenoSeven on 2017/7/25.
 */

public class MailForwardingStateReceiver extends FilteredBroadcastReceiver {
    public final static String MAIL_SENDING_RESULT = MailForwardingStateReceiver.class.getName() + ".SMS_SENDING_RESULT";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(MAIL_SENDING_RESULT)) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    ToastUtil.showToast(context, "Mail Sent");
                    break;
                default:
                    ToastUtil.showToast(context, "Mail Sending failed");
                    break;
            }
        }
    }

    @NonNull
    @Override
    public IntentFilter getIntentFilter() {
        return new IntentFilter(MAIL_SENDING_RESULT);
    }

}