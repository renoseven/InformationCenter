package net.renoseven.informationcenter.processor;

import android.content.Intent;
import android.util.Log;

import net.grandcentrix.tray.TrayPreferences;
import net.renoseven.informationcenter.message.MessageHolder;
import net.renoseven.informationcenter.message.MessageType;
import net.renoseven.informationcenter.preference.ApplicationPreferences;
import net.renoseven.informationcenter.preference.MailPreferences;
import net.renoseven.util.PreferencesUtil;

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static net.renoseven.informationcenter.preference.MailPreferences.CONFIG_MAIL_AUTH_PASSWORD;
import static net.renoseven.informationcenter.preference.MailPreferences.CONFIG_MAIL_AUTH_USERNAME;

/**
 * Mail Forwarding Processor
 * Created by RenoSeven on 2016/9/22.
 */
public class MailForwardingProcessor extends BaseMessageProcessor {
    public final static String MAIL_SENT = MailForwardingProcessor.class.getName() + ".MAIL_SENT";



    @Override
    protected void doTask() throws Exception {
        Log.i(TAG, "Forward as mail");
        TrayPreferences appPref = preferencesMap.get(ApplicationPreferences.MODULE_NAME);
        TrayPreferences mailPref = preferencesMap.get(MailPreferences.MODULE_NAME);

        Properties serverConfig = PreferencesUtil.convertToProperties(mailPref);
        serverConfig.setProperty(MailPreferences.CONFIG_MAIL_DEBUG, "false");
        serverConfig.setProperty(MailPreferences.CONFIG_MAIL_TRANSPORT_PROTOCOL, "smtp");

        MessageHolder mail = (MessageHolder) message.clone();
        mail.setMsgType(MessageType.MAIL);
        mail.setSubject(message.getSender());
        mail.setSenderName(appPref.getString(ApplicationPreferences.CONFIG_FORWARDING_MAIL_SENDER_NAME, null));
        mail.setSender(appPref.getString(ApplicationPreferences.CONFIG_FORWARDING_MAIL_SENDER, null));
        mail.setReceiverName(appPref.getString(ApplicationPreferences.CONFIG_FORWARDING_MAIL_RECEIVER_NAME, null));
        mail.setReceiver(appPref.getString(ApplicationPreferences.CONFIG_FORWARDING_MAIL_RECEIVER, null));
        Log.i(TAG, mail.toString());

        Log.v(TAG, "Starting session...");
        Session session = Session.getInstance(serverConfig);

        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(mail.getSender(), mail.getSenderName(), mail.getCharset()));
        mimeMessage.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(mail.getReceiver(), mail.getReceiverName(), mail.getCharset()));
        mimeMessage.setSubject(mail.getSubject(), mail.getCharset());
        mimeMessage.setSentDate(new Date(mail.getTimeStamp()));
        mimeMessage.setContent(mail.getText(), "text/html;charset=" + mail.getCharset());

        Log.v(TAG, "Connecting to server...");
        Transport transport = session.getTransport();
        transport.connect(serverConfig.getProperty(CONFIG_MAIL_AUTH_USERNAME), serverConfig.getProperty(CONFIG_MAIL_AUTH_PASSWORD));

        Log.v(TAG, "Sending mail...");
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

        transport.close();
    }

    @Override
    protected void onTaskFinished() {
        Log.i(TAG, "Mail sent");
        context.sendBroadcast(new Intent(MAIL_SENT));
    }

    @Override
    protected void onTaskFailed(Exception e) {
        Log.e(TAG, "Mail sending failed");
    }
}
