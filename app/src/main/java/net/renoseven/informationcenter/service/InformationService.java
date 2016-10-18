package net.renoseven.informationcenter.service;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import net.renoseven.framework.NIAService;
import net.renoseven.informationcenter.message.MessageHolder;
import net.renoseven.informationcenter.message.MessageType;
import net.renoseven.informationcenter.preference.ApplicationPreferences;
import net.renoseven.informationcenter.processor.MailProcessor;
import net.renoseven.informationcenter.processor.MessageProcessor;
import net.renoseven.informationcenter.processor.SMSMessageProcessor;
import net.renoseven.informationcenter.receiver.MessageReceiver;
import net.renoseven.informationcenter.receiver.SMSReceiver;
import net.renoseven.util.PreferencesUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Core Service Implementation
 * Created by RenoSeven on 2016/9/8.
 */
public class InformationService extends NIAService {
    private final BroadcastReceiver messageReceiver;
    private final BroadcastReceiver smsReceiver;
    private final Map<MessageType, MessageProcessor> messageProcessors;

    private Properties applicationSettings;

    public InformationService() {
        super();
        smsReceiver = new SMSReceiver();
        messageReceiver = new MessageReceiver() {
            @Override
            protected void onMessageReceived(MessageHolder msg) {
                Log.i(TAG, "Message received");
                processMessage(msg);
            }
        };
        messageProcessors = new HashMap<>();
    }

    @Override
    protected void onServiceBorn() {
        // load configurations
        Log.v(TAG, "Loading settings...");
        applicationSettings = new Properties();
        PreferencesUtil.convert(new ApplicationPreferences(this), applicationSettings);
        Log.d(TAG, applicationSettings.toString());

        // register receivers
        Log.v(TAG, "Registering receivers...");
        IntentFilter smsActionFilter = new IntentFilter();
        smsActionFilter.addAction(SMSReceiver.SMS_RECEIVED);
        registerReceiver(smsReceiver, smsActionFilter);

        IntentFilter messageActionFilter = new IntentFilter();
        messageActionFilter.addAction(MessageReceiver.MESSAGE_RECEIVED);
        registerReceiver(messageReceiver, messageActionFilter);

        // register message processors
        Log.v(TAG, "Registering message processors...");
        messageProcessors.put(MessageType.SMS, SMSMessageProcessor.getInstance());
        messageProcessors.put(MessageType.MAIL, MailProcessor.getInstance());
    }

    @Override
    protected void onServiceDead() {
        Log.v(TAG, "Unregistering receivers...");
        unregisterReceiver(smsReceiver);
        unregisterReceiver(messageReceiver);
    }

    @Override
    protected Bundle onServiceUpdate(Bundle request) {
        return null;
    }

    private void processMessage(MessageHolder msg) {
        Log.i(TAG, msg.toString());

        MessageType type = msg.getMsgType();
        Log.d(TAG, "Message Type: "+ type.toString());

        MessageProcessor processor = messageProcessors.get(type);
        if(processor != null) {
            Log.d(TAG, "Processor: "+ processor);
            processor.processMessage(applicationSettings, msg);
        }
        else {
            Log.e(TAG, "Unsupported message type");
        }
    }
}