package net.renoseven.informationcenter.processor;

import net.grandcentrix.tray.TrayPreferences;
import net.renoseven.informationcenter.message.MessageHolder;

import java.util.Map;

/**
 * Message Processor Interface
 * Created by RenoSeven on 2016/9/22.
 */

public interface MessageProcessor {
    void processMessage(final Map<String, TrayPreferences> preferencesMap, MessageHolder message);
}
