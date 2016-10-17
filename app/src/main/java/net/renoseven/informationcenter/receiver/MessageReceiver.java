package net.renoseven.informationcenter.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import net.renoseven.informationcenter.message.MessageHolder;

/**
 * Universal MessageHolder Receiver
 * Created by RenoSeven on 2016/9/8.
 */
public abstract class MessageReceiver extends BroadcastReceiver {
    protected final static String TAG = MessageReceiver.class.getSimpleName();
    protected final static String CLASS_NAME = MessageReceiver.class.getName();
    public final static String MESSAGE_RECEIVED = CLASS_NAME + ".MESSAGE_RECEIVED";
    public final static String MESSAGE_CONTENT = CLASS_NAME + ".MESSAGE_CONTENT";

    protected abstract void onMessageReceived(MessageHolder msg);

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(MESSAGE_RECEIVED)) {

            MessageHolder msg = (MessageHolder) intent.getSerializableExtra(MESSAGE_CONTENT);

            onMessageReceived(msg);
        }
    }
}