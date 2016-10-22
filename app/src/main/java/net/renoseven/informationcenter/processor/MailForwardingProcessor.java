package net.renoseven.informationcenter.processor;

import android.content.Context;
import android.util.Log;

import net.grandcentrix.tray.TrayPreferences;
import net.renoseven.informationcenter.message.MessageHolder;
import net.renoseven.informationcenter.message.MessageType;
import net.renoseven.informationcenter.preference.ApplicationPreferences;
import net.renoseven.informationcenter.preference.MailPreferences;
import net.renoseven.informationcenter.task.MailSendingTask;
import net.renoseven.util.PreferencesUtil;

import java.util.Map;
import java.util.Properties;

/**
 * Mail Forwarding Processor
 * Created by RenoSeven on 2016/9/22.
 */
public class MailForwardingProcessor extends BaseMessageProcessor implements MessageProcessor {

    public MailForwardingProcessor(Context base) {
        super(base);
    }

    @Override
    public void processMessage(final Map<String, TrayPreferences> preferencesMap, MessageHolder message) {
        final TrayPreferences appPref = preferencesMap.get(ApplicationPreferences.MODULE_NAME);
        final TrayPreferences mailPref = preferencesMap.get(MailPreferences.MODULE_NAME);

        Log.i(TAG, "Forward as mail");

        try {
            Properties mailSendingConfig = PreferencesUtil.convertToProperties(mailPref);
            mailSendingConfig.setProperty(MailPreferences.CONFIG_MAIL_DEBUG, "false");
            mailSendingConfig.setProperty(MailPreferences.CONFIG_MAIL_TRANSPORT_PROTOCOL, "smtp");

            MessageHolder mail = (MessageHolder) message.clone();
            mail.setMsgType(MessageType.MAIL);
            mail.setSubject(message.getSender());
            mail.setSenderName(appPref.getString(ApplicationPreferences.CONFIG_FORWARDING_MAIL_SENDER_NAME, null));
            mail.setSender(appPref.getString(ApplicationPreferences.CONFIG_FORWARDING_MAIL_SENDER, null));
            mail.setReceiverName(appPref.getString(ApplicationPreferences.CONFIG_FORWARDING_MAIL_RECEIVER_NAME, null));
            mail.setReceiver(appPref.getString(ApplicationPreferences.CONFIG_FORWARDING_MAIL_RECEIVER, null));
            Log.i(TAG, mail.toString());

            Log.d(TAG, "Starting job...");
            this.runTask(new MailSendingTask(getBaseContext(), mailSendingConfig, mail));
        } catch (CloneNotSupportedException e) {
            Log.e(TAG, e.toString());
        }
    }
}
