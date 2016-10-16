package net.renoseven.informationcenter.task;

import android.util.Log;

import com.sun.mail.smtp.SMTPSendFailedException;

import net.renoseven.informationcenter.message.MessageHolder;
import net.renoseven.informationcenter.message.MessageType;

import java.util.Date;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Task of sending an email
 * Created by RenoSeven on 2016/9/9.
 */
public class MailSendingTask implements Runnable {
    private final static String CONFIG_SMTP_USERNAME = "mail.auth.username";
    private final static String CONFIG_SMTP_PASSWORD = "mail.auth.password";

    protected final String TAG = this.toString();
    private Properties serverConfig;
    private MessageHolder message;

    public MailSendingTask(Properties serverConfig, MessageHolder message) {
        this.serverConfig = serverConfig;
        this.message = message;
    }

    @Override
    public void run() {
        if (message.getMsgType() != MessageType.MAIL) {
            Log.w(TAG, "Wrong message type");
            return;
        }
        //TODO: show mail sending status
        try {
            Log.d(TAG, "Applying settings...");
//            serverConfig.setProperty("mail.debug", "true");
            serverConfig.setProperty("mail.transport.protocol", "smtp");

            Log.d(TAG, "Starting session...");
            Session session = Session.getInstance(serverConfig);

            MimeMessage mail = new MimeMessage(session);
            mail.setFrom(new InternetAddress(message.getSender(), message.getSenderName(), message.getCharset()));
            mail.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(message.getReceiver(), message.getReceiverName(), message.getCharset()));
            mail.setSubject(message.getSubject(), message.getCharset());
            mail.setSentDate(new Date(message.getTimeStamp()));
            mail.setContent(message.getText(), "text/html;charset=" + message.getCharset());

            Log.d(TAG, "Connecting to server...");
            Transport transport = session.getTransport();
            transport.connect(serverConfig.getProperty(CONFIG_SMTP_USERNAME), serverConfig.getProperty(CONFIG_SMTP_PASSWORD));

            Log.d(TAG, "Sending mail...");
            transport.sendMessage(mail, mail.getAllRecipients());

            transport.close();
            Log.i(TAG, "Mail sent");
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

}
