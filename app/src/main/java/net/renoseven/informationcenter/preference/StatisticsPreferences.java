package net.renoseven.informationcenter.preference;

import android.content.Context;

import net.grandcentrix.tray.TrayPreferences;

/**
 * Application Preferences
 * Created by RenoSeven on 2016/10/17.
 */
public class StatisticsPreferences extends TrayPreferences {
    private final static String MODULE_NAME = StatisticsPreferences.class.getSimpleName();
    private final static int DB_VERSION = 1;

    public final static String STAT_RUNNING_TIME = "stat.uptime";
    public final static String STAT_MESSAGE_RECEIVED = "stat.message.received";
    public final static String STAT_SMS_SENT = "stat.sms.sent";
    public final static String STAT_MAIL_SENT = "stat.mail.sent";

    public StatisticsPreferences(Context context) {
        super(context, MODULE_NAME, DB_VERSION);
    }
}