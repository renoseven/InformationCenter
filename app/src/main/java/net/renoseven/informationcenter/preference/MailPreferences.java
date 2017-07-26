package net.renoseven.informationcenter.preference;

import android.content.Context;

import net.grandcentrix.tray.TrayPreferences;

/**
 * Mail Preferences
 * Created by RenoSeven on 2016/10/22.
 */

public class MailPreferences extends TrayPreferences {
    public final static String MODULE_NAME = "mail";
    // Server settings
    public final static String CONFIG_MAIL_DEBUG                = MODULE_NAME + ".debug";
    public final static String CONFIG_MAIL_AUTH_USERNAME        = MODULE_NAME + ".auth.username";
    public final static String CONFIG_MAIL_AUTH_PASSWORD        = MODULE_NAME + ".auth.password";
    public final static String CONFIG_MAIL_TRANSPORT_PROTOCOL   = MODULE_NAME + ".transport.protocol";
    public final static String CONFIG_MAIL_SMTP_HOST            = MODULE_NAME + ".smtp.host";
    public final static String CONFIG_MAIL_SMTP_PORT            = MODULE_NAME + ".smtp.port";
    public final static String CONFIG_MAIL_SMTP_SSL_ENABLED     = MODULE_NAME + ".smtp.ssl.enable";
    public final static String CONFIG_MAIL_SMTP_TLS_ENABLED     = MODULE_NAME + ".smtp.tls.enable";
    private final static int DB_VERSION = 1;

    public MailPreferences(Context context) {
        super(context, MODULE_NAME, DB_VERSION);
    }
}
