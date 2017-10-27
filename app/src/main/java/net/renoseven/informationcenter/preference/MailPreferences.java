package net.renoseven.informationcenter.preference;

import android.content.Context;

import net.grandcentrix.tray.TrayPreferences;

/**
 * Mail Preferences
 * Created by RenoSeven on 2016/10/22.
 */

public class MailPreferences extends TrayPreferences {
    public final static String DB_NAME = "mail";
    private final static int DB_VERSION = 1;

    // Server settings
    public final static String CONFIG_MAIL_DEBUG                = DB_NAME + ".debug";
    public final static String CONFIG_MAIL_AUTH_USERNAME        = DB_NAME + ".auth.username";
    public final static String CONFIG_MAIL_AUTH_PASSWORD        = DB_NAME + ".auth.password";
    public final static String CONFIG_MAIL_TRANSPORT_PROTOCOL   = DB_NAME + ".transport.protocol";
    public final static String CONFIG_MAIL_SMTP_HOST            = DB_NAME + ".smtp.host";
    public final static String CONFIG_MAIL_SMTP_PORT            = DB_NAME + ".smtp.port";
    public final static String CONFIG_MAIL_SMTP_SSL_ENABLED     = DB_NAME + ".smtp.ssl.enable";
    public final static String CONFIG_MAIL_SMTP_TLS_ENABLED     = DB_NAME + ".smtp.tls.enable";

    public MailPreferences(Context context) {
        super(context, DB_NAME, DB_VERSION);
    }
}
