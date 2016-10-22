package net.renoseven.informationcenter.preference;

import android.content.Context;

import net.grandcentrix.tray.TrayPreferences;

/**
 * Application Preferences
 * Created by RenoSeven on 2016/10/17.
 */
public class ApplicationPreferences extends TrayPreferences {
    public final static String MODULE_NAME = "app";
    private final static int DB_VERSION = 1;

    // Receiver settings
    public final static String CONFIG_RECEIVER_SMS_ENABLED          = MODULE_NAME + ".receiver.sms.enable";
    public final static String CONFIG_RECEIVER_MAIL_ENABLED         = MODULE_NAME + ".receiver.mail.enable";

    // Forwarding settings
    public final static String CONFIG_FORWARDING_SMS_ENABLED        = MODULE_NAME + ".forwarding.sms.enable";
    public final static String CONFIG_FORWARDING_SMS_RECEIVER       = MODULE_NAME + ".forwarding.sms.receiver";
    public final static String CONFIG_FORWARDING_MAIL_ENABLED       = MODULE_NAME + ".forwarding.mail.enable";
    public final static String CONFIG_FORWARDING_MAIL_SENDER        = MODULE_NAME + ".forwarding.mail.sender";
    public final static String CONFIG_FORWARDING_MAIL_SENDER_NAME   = MODULE_NAME + ".forwarding.mail.sender.name";
    public final static String CONFIG_FORWARDING_MAIL_RECEIVER      = MODULE_NAME + ".forwarding.mail.receiver";
    public final static String CONFIG_FORWARDING_MAIL_RECEIVER_NAME = MODULE_NAME + ".forwarding.mail.receiver.name";

    public ApplicationPreferences(Context context) {
        super(context, MODULE_NAME, DB_VERSION);
    }
}