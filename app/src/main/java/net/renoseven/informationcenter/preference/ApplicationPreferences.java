package net.renoseven.informationcenter.preference;

import android.content.Context;

import net.grandcentrix.tray.TrayPreferences;

/**
 * Application Preferences
 * Created by RenoSeven on 2016/10/17.
 */
public class ApplicationPreferences extends TrayPreferences {
    private final static String MODULE_NAME = ApplicationPreferences.class.getSimpleName();
    private final static int DB_VERSION = 1;

    public final static String CONFIG_MAIL_DEBUG                    = "mail.debug";
    public final static String CONFIG_MAIL_SMTP_HOST                = "mail.smtp.host";
    public final static String CONFIG_MAIL_SMTP_PORT                = "mail.smtp.port";
    public final static String CONFIG_MAIL_AUTH_USERNAME            = "mail.auth.username";
    public final static String CONFIG_MAIL_AUTH_PASSWORD            = "mail.auth.password";
    public final static String CONFIG_MAIL_FORWARDING_MAIL_ENABLED  = "mail.forwarding.enabled";
    public final static String CONFIG_MAIL_FORWARDING_SENDER        = "mail.forwarding.sender";
    public final static String CONFIG_MAIL_FORWARDING_SENDER_NAME   = "mail.forwarding.sender.name";
    public final static String CONFIG_MAIL_FORWARDING_RECEIVER      = "mail.forwarding.receiver";
    public final static String CONFIG_MAIL_FORWARDING_RECEIVER_NAME = "mail.forwarding.receiver.name";

    public ApplicationPreferences(Context context) {
        super(context, MODULE_NAME, DB_VERSION);
    }
}