package net.renoseven.informationcenter.preference;

import android.content.Context;

import net.grandcentrix.tray.TrayPreferences;

/**
 * Application Preferences
 * Created by RenoSeven on 2016/10/17.
 */
public class ApplicationPreferences extends TrayPreferences {
    public final static String DB_NAME = "app";
    private final static int DB_VERSION = 1;

    public final static String CONFIG_RECEIVER_SMS_ENABLED          = DB_NAME + ".module.smsmonitoronitor.SMSMonitorModule";

    public final static String CONFIG_RECEIVER_MAIL_ENABLED         = DB_NAME + ".module.mailmonitor.MailMonitorModule";

    public final static String CONFIG_FORWARDING_SMS_ENABLED        = DB_NAME + ".module.smsforwarding.SMSForwardingModule";
    public final static String CONFIG_FORWARDING_SMS_RECEIVER       = DB_NAME + ".module.smsforwarding.SMSForwardingModule.receiver";

    public final static String CONFIG_FORWARDING_MAIL_ENABLED       = DB_NAME + ".module.mailforwarding.MailForwardingModule";
    public final static String CONFIG_FORWARDING_MAIL_SENDER        = DB_NAME + ".module.mailforwarding.MailForwardingModule.sender";
    public final static String CONFIG_FORWARDING_MAIL_SENDER_NAME   = DB_NAME + ".module.mailforwarding.MailForwardingModule.sender.name";
    public final static String CONFIG_FORWARDING_MAIL_RECEIVER      = DB_NAME + ".module.mailforwarding.MailForwardingModule.receiver";
    public final static String CONFIG_FORWARDING_MAIL_RECEIVER_NAME = DB_NAME + ".module.mailforwarding.MailForwardingModule.receiver.name";

    public ApplicationPreferences(Context context) {
        super(context, DB_NAME, DB_VERSION);
    }
}