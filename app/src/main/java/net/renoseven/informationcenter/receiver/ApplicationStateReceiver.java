package net.renoseven.informationcenter.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;

import net.grandcentrix.tray.TrayPreferences;
import net.renoseven.framework.FilteredBroadcastReceiver;

import static net.renoseven.informationcenter.preference.StatisticsPreferences.STAT_MAIL_SENT;
import static net.renoseven.informationcenter.preference.StatisticsPreferences.STAT_MESSAGE_RECEIVED;
import static net.renoseven.informationcenter.preference.StatisticsPreferences.STAT_SMS_SENT;
import static net.renoseven.informationcenter.receiver.MessageReceiver.MESSAGE_RECEIVED;
import static net.renoseven.informationcenter.task.MailSendingTask.MAIL_SENT;
import static net.renoseven.informationcenter.task.SMSSendingTask.SMS_SENT;

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
        } else if (action.equals(SMS_SENT)) {
            int smsSent = statPref.getInt(STAT_SMS_SENT, 0);
            statPref.put(STAT_SMS_SENT, smsSent + 1);
        } else if (action.equals(MAIL_SENT)) {
            int mailSent = statPref.getInt(STAT_MAIL_SENT, 0);
            statPref.put(STAT_MAIL_SENT, mailSent + 1);
        }
    }

    @NonNull
    @Override
    public IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(MESSAGE_RECEIVED);
        filter.addAction(SMS_SENT);
        filter.addAction(MAIL_SENT);
        return filter;
    }
}
