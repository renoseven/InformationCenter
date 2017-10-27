package net.renoseven.informationcenter.preference;

import android.content.Context;

import net.grandcentrix.tray.TrayPreferences;

/**
 * Application Preferences
 * Created by RenoSeven on 2016/10/17.
 */
public class StatisticsPreferences extends TrayPreferences {
    public final static String DB_NAME = "stat";
    private final static int DB_VERSION = 1;

    public final static String STAT_MESSAGE_RECEIVED    = DB_NAME + ".message.received";
    public final static String STAT_SMS_SENT            = DB_NAME + ".sms.sent";
    public final static String STAT_MAIL_SENT           = DB_NAME + ".mail.sent";

    public StatisticsPreferences(Context context) {
        super(context, DB_NAME, DB_VERSION);
    }
}