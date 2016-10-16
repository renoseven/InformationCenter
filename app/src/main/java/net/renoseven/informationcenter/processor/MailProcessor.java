package net.renoseven.informationcenter.processor;

import net.renoseven.informationcenter.message.MessageHolder;

import java.util.Properties;

/**
 * Singleton Mail Processor
 * Created by RenoSeven on 2016/9/22.
 */

public class MailProcessor implements MessageProcessor {
    private static class SingletonHolder {
        private static final MessageProcessor INSTANCE = new MailProcessor();
    }

    private MailProcessor() {}

    public static MessageProcessor getInstance() {
        return SingletonHolder.INSTANCE;
    }
    @Override
    public void processMessage(Properties settings, MessageHolder message) {
        // TODO: implement this
    }
}