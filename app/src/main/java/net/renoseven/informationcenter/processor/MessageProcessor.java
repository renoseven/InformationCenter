package net.renoseven.informationcenter.processor;

import net.renoseven.informationcenter.message.MessageHolder;

import java.util.Properties;

/**
 * Message Processor Interface
 * Created by RenoSeven on 2016/9/22.
 */

public interface MessageProcessor {
    void processMessage(final Properties settings, MessageHolder message);
}
