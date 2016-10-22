package net.renoseven.informationcenter.task;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import net.renoseven.informationcenter.message.MessageHolder;
import net.renoseven.informationcenter.message.MessageType;

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static net.renoseven.informationcenter.preference.MailPreferences.CONFIG_MAIL_AUTH_PASSWORD;
import static net.renoseven.informationcenter.preference.MailPreferences.CONFIG_MAIL_AUTH_USERNAME;

/**
 * Task of sending an email
 * Created by RenoSeven on 2016/9/9.
 */
public class MailSendingTask extends BaseTask {
    public final static String MAIL_SENT = MailSendingTask.class.getName() + ".MAIL_SENT";

    private Properties serverConfig;
    private MessageHolder message;

    public MailSendingTask(final Context base, final Properties mailConfig, final MessageHolder message) {
        super(base);
        this.serverConfig = mailConfig;
        this.message = message;
    }

    @Override
    public boolean doTask() {
        boolean result = false;
        if (message.getMsgType() != MessageType.MAIL) {
            Log.e(TAG, "Wrong message type");
        }
        try {
            Log.v(TAG, "Starting session...");
            Session session = Session.getInstance(serverConfig);

            MimeMessage mail = new MimeMessage(session);
            mail.setFrom(new InternetAddress(message.getSender(), message.getSenderName(), message.getCharset()));
            mail.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(message.getReceiver(), message.getReceiverName(), message.getCharset()));
            mail.setSubject(message.getSubject(), message.getCharset());
            mail.setSentDate(new Date(message.getTimeStamp()));
            mail.setContent(message.getText(), "text/html;charset=" + message.getCharset());

            Log.v(TAG, "Connecting to server...");
            Transport transport = session.getTransport();
            transport.connect(serverConfig.getProperty(CONFIG_MAIL_AUTH_USERNAME), serverConfig.getProperty(CONFIG_MAIL_AUTH_PASSWORD));

            Log.v(TAG, "Sending mail...");
            transport.sendMessage(mail, mail.getAllRecipients());

            transport.close();
            result = true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return result;
    }

    @Override
    protected void onTaskFinished() {
        Log.i(TAG, "Mail sent");
        sendBroadcast(new Intent(MAIL_SENT));
    }

    @Override
    protected void onTaskFailed() {
        Log.e(TAG, "Mail sending failed");
    }
}