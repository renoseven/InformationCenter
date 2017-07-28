package net.renoseven.informationcenter.module.mailforwarding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.util.Log;

import net.renoseven.framework.FilteredBroadcastReceiver;
import net.renoseven.util.ToastUtil;

/**
 * Mail Forwarding State Receiver
 * Created by RenoSeven on 2017/7/25.
 */

public class MailForwardingStateReceiver extends FilteredBroadcastReceiver {
    public final static String MAIL_SENDING_RESULT = MailForwardingStateReceiver.class.getName() + ".MAIL_SENDING_RESULT";
    protected final static String TAG = MailForwardingStateReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(MAIL_SENDING_RESULT)) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Log.i(TAG, "Mail sent");
                    ToastUtil.showToast(context, "Mail sent");
                    break;
                default:
                    Log.i(TAG, "Mail sending failed");
                    ToastUtil.showToast(context, "Mail sending failed");
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
