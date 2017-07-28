package net.renoseven.informationcenter.core.receiver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;

import net.grandcentrix.tray.TrayPreferences;
import net.renoseven.framework.FilteredBroadcastReceiver;

import static net.renoseven.informationcenter.core.receiver.MessageReceiver.MESSAGE_RECEIVED;
import static net.renoseven.informationcenter.module.mailforwarding.MailForwardingStateReceiver.MAIL_SENDING_RESULT;
import static net.renoseven.informationcenter.module.smsforwarding.SMSForwardingStateReceiver.SMS_SENDING_RESULT;
import static net.renoseven.informationcenter.preference.StatisticsPreferences.STAT_MAIL_SENT;
import static net.renoseven.informationcenter.preference.StatisticsPreferences.STAT_MESSAGE_RECEIVED;
import static net.renoseven.informationcenter.preference.StatisticsPreferences.STAT_SMS_SENT;

/**
 * Application State Receiver
 * Created by RenoSeven on 2016/10/22.
 */

public class ApplicationStateReceiver extends FilteredBroadcastReceiver {

    private final TrayPreferences statPref;

    public ApplicationStateReceiver(TrayPreferences preferences) {
        statPref = preferences;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(MESSAGE_RECEIVED)) {
            int smsSent = statPref.getInt(STAT_MESSAGE_RECEIVED, 0);
            statPref.put(STAT_MESSAGE_RECEIVED, smsSent + 1);
        } else if (action.equals(SMS_SENDING_RESULT) && getResultCode() == Activity.RESULT_OK) {
            int smsSent = statPref.getInt(STAT_SMS_SENT, 0);
            statPref.put(STAT_SMS_SENT, smsSent + 1);
        } else if (action.equals(MAIL_SENDING_RESULT) && getResultCode() == Activity.RESULT_OK) {
            int mailSent = statPref.getInt(STAT_MAIL_SENT, 0);
            statPref.put(STAT_MAIL_SENT, mailSent + 1);
        }
    }

    @NonNull
    @Override
    public IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(MESSAGE_RECEIVED);
        filter.addAction(SMS_SENDING_RESULT);
        filter.addAction(MAIL_SENDING_RESULT);
        return filter;
    }

    @Override
    public String getPermission() {
        return null;
    }
}
