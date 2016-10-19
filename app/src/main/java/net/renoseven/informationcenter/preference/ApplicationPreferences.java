package net.renoseven.informationcenter.preference;

import android.content.Context;
import android.content.res.Resources;

import net.grandcentrix.tray.TrayPreferences;
import net.renoseven.informationcenter.R;

/**
 * Application Preferences
 * Created by RenoSeven on 2016/10/17.
 */
public class ApplicationPreferences extends TrayPreferences {
    private final static String MODULE_NAME = ApplicationPreferences.class.getSimpleName();
    private final static int DB_VERSION = 1;

    // values should match to strings.xml

    // Receiver settings
    public final static String CONFIG_RECEIVER_SMS_ENABLED          = "receiver.sms.enable";
    public final static String CONFIG_RECEIVER_MAIL_ENABLED         = "receiver.mail.enable";

    // Mail settings
    public final static String CONFIG_MAIL_AUTH_USERNAME            = "mail.auth.username";
    public final static String CONFIG_MAIL_AUTH_PASSWORD            = "mail.auth.password";
    public final static String CONFIG_MAIL_SMTP_HOST                = "mail.smtp.host";
    public final static String CONFIG_MAIL_SMTP_PORT                = "mail.smtp.port";
    public final static String CONFIG_MAIL_SMTP_SSL_ENABLED         = "mail.smtp.ssl.enable";
    public final static String CONFIG_MAIL_SMTP_TLS_ENABLED         = "mail.smtp.tls.enable";

    // Forwarding settings
    public final static String CONFIG_FORWARDING_SMS_ENABLED        = "forwarding.sms.enable";
    public final static String CONFIG_FORWARDING_SMS_RECEIVER       = "forwarding.sms.receiver";

    public final static String CONFIG_FORWARDING_MAIL_ENABLED       = "forwarding.mail.enable";
    public final static String CONFIG_FORWARDING_MAIL_SENDER        = "forwarding.mail.sender";
    public final static String CONFIG_FORWARDING_MAIL_SENDER_NAME   = "forwarding.mail.sender.name";
    public final static String CONFIG_FORWARDING_MAIL_RECEIVER      = "forwarding.mail.receiver";
    public final static String CONFIG_FORWARDING_MAIL_RECEIVER_NAME = "forwarding.mail.receiver.name";

    public ApplicationPreferences(Context context) {
        super(context, MODULE_NAME, DB_VERSION);
    }
}