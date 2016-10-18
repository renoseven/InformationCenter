package net.renoseven.informationcenter.processor;

import android.util.Log;

import net.renoseven.informationcenter.message.MessageHolder;
import net.renoseven.informationcenter.message.MessageType;
import net.renoseven.informationcenter.preference.ApplicationPreferences;
import net.renoseven.informationcenter.task.MailSendingTask;
import net.renoseven.informationcenter.task.SMSSendingTask;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Singleton SMS Processor
 * Created by RenoSeven on 2016/9/22.
 */
public class SMSMessageProcessor implements MessageProcessor {
    private final static String TAG = SMSMessageProcessor.class.getSimpleName();

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private static class SingletonHolder {
        private static final MessageProcessor INSTANCE = new SMSMessageProcessor();
    }

    private SMSMessageProcessor() {}

    public static MessageProcessor getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void processMessage(final Properties settings, MessageHolder msg) {
        if(msg.getMsgType() != MessageType.SMS) {
            return;
        }

        String text = msg.getText();
        // TODO: check configuration of sms command format
        if (text.startsWith("SEND#")) {
            String command[] = text.split("#");
            if (command.length <= 2) return;

            Log.d(TAG, "SMS Command");
            MessageHolder reply = new MessageHolder(MessageType.SMS);
            reply.setReceiver(command[1]);
            reply.setText(command[2]);

            Log.d(TAG, "Send new SMS");
            executorService.execute(new SMSSendingTask(reply));
        }
        else {
            Log.i(TAG, "Forward as mail");
            try {
                MessageHolder mail = (MessageHolder) msg.clone();
                mail.setMsgType(MessageType.MAIL);
                mail.setCharset(msg.getCharset());
                mail.setTimeStamp(msg.getTimeStamp());
                mail.setSubject(msg.getSender());
                mail.setSenderName(settings.getProperty(ApplicationPreferences.CONFIG_FORWARDING_MAIL_SENDER_NAME));
                mail.setSender(settings.getProperty(ApplicationPreferences.CONFIG_FORWARDING_MAIL_SENDER));
                mail.setReceiverName(settings.getProperty(ApplicationPreferences.CONFIG_FORWARDING_MAIL_RECEIVER_NAME));
                mail.setReceiver(settings.getProperty(ApplicationPreferences.CONFIG_FORWARDING_MAIL_RECEIVER));
                mail.setText(msg.getText());
                Log.i(TAG, mail.toString());

                Log.d(TAG, "Starting job...");
                executorService.execute(new MailSendingTask(settings, mail));
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }
}
