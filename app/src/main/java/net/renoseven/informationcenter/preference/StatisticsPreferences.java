package net.renoseven.informationcenter.preference;

import android.content.Context;

import net.grandcentrix.tray.TrayPreferences;

/**
 * Application Preferences
 * Created by RenoSeven on 2016/10/17.
 */
public class StatisticsPreferences extends TrayPreferences {
    public final static String MODULE_NAME = "stat";
    public final static String STAT_MESSAGE_RECEIVED    = MODULE_NAME + ".message.received";
    public final static String STAT_SMS_SENT            = MODULE_NAME + ".sms.sent";
    public final static String STAT_MAIL_SENT           = MODULE_NAME + ".mail.sent";
    private final static int DB_VERSION = 1;

    public StatisticsPreferences(Context context) {
        super(context, MODULE_NAME, DB_VERSION);
    }
}