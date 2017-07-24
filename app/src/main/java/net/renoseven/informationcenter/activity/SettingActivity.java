package net.renoseven.informationcenter.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;

import net.grandcentrix.tray.TrayPreferences;
import net.renoseven.framework.ExtendedActivity;
import net.renoseven.informationcenter.R;
import net.renoseven.informationcenter.preference.ApplicationPreferences;
import net.renoseven.informationcenter.preference.MailPreferences;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SettingActivity extends ExtendedActivity implements CompoundButton.OnCheckedChangeListener {
    private final String TAG = SettingActivity.class.getSimpleName();
    private final Map<String, TrayPreferences> preferencesMap;

    private View smsForwardingSettings;
    private CompoundButton smsForwardingEnable;
    private View mailForwardingSettings;
    private CompoundButton mailForwardingEnable;

    public SettingActivity() {
        preferencesMap = new HashMap<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        preferencesMap.put(ApplicationPreferences.MODULE_NAME, new ApplicationPreferences(this));
        preferencesMap.put(MailPreferences.MODULE_NAME, new MailPreferences(this));

        loadSettings();
        initComponents();
    }

    @Override
    protected void onDestroy() {
        saveSettings();
        super.onDestroy();
    }

    private void initComponents() {
        // sms forwarding settings
        smsForwardingSettings = findViewById(R.id.layout_sms_forwarding_setting);
        smsForwardingEnable = findViewById(R.id.sw_sms_forwarding_enable);
        smsForwardingEnable.setOnCheckedChangeListener(this);

        // mail forwarding settings
        mailForwardingSettings = findViewById(R.id.layout_mail_forwarding_setting);
        mailForwardingEnable = findViewById(R.id.sw_mail_forwarding_enable);
        mailForwardingEnable.setOnCheckedChangeListener(this);

        updateUI();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        updateUI();
    }

    private void updateUI() {
        // sms forwarding settings
        if (!smsForwardingEnable.isChecked()) {
            smsForwardingSettings.setVisibility(View.GONE);
        } else {
            smsForwardingSettings.setVisibility(View.VISIBLE);
        }
        // mail forwarding settings
        if (!mailForwardingEnable.isChecked()) {
            mailForwardingSettings.setVisibility(View.GONE);
        } else {
            mailForwardingSettings.setVisibility(View.VISIBLE);
        }
    }

    private void loadSettings() {
        Log.d(TAG, "Reading settings...");
        Set<View> textSet = findViewsByClass(EditText.class);
        for (View view : textSet) {
            String viewTag = (String) view.getTag();
            for (String moduleName : preferencesMap.keySet()) {
                if (viewTag.startsWith(moduleName)) {
                    TrayPreferences pref = preferencesMap.get(moduleName);
                    String text = pref.getString(viewTag, null);
                    ((EditText) view).setText(text);
                    Log.v(TAG, viewTag + "=" + text);
                }
            }
        }

        Set<View> buttonSet = findViewsByClass(CompoundButton.class);
        for (View view : buttonSet) {
            String viewTag = (String) view.getTag();
            for (String moduleName : preferencesMap.keySet()) {
                if (viewTag.startsWith(moduleName)) {
                    TrayPreferences pref = preferencesMap.get(moduleName);
                    boolean value = pref.getBoolean(viewTag, false);
                    ((CompoundButton) view).setChecked(value);
                    Log.v(TAG, viewTag + "=" + value);
                }
            }
        }
        Log.i(TAG, "Settings loaded");
    }

    private void saveSettings() {
        Log.d(TAG, "Saving changes...");
        Set<View> textSet = findViewsByClass(EditText.class);
        for (View view : textSet) {
            String viewTag = (String) view.getTag();
            for (String moduleName : preferencesMap.keySet()) {
                if (viewTag.startsWith(moduleName)) {
                    TrayPreferences pref = preferencesMap.get(moduleName);
                    String text = ((EditText) view).getText().toString();
                    pref.put(viewTag, text);
                    Log.v(TAG, viewTag + "=" + text);
                }
            }
        }
        Set<View> buttonSet = findViewsByClass(CompoundButton.class);
        for (View view : buttonSet) {
            String viewTag = (String) view.getTag();
            for (String moduleName : preferencesMap.keySet()) {
                if (viewTag.startsWith(moduleName)) {
                    TrayPreferences pref = preferencesMap.get(moduleName);
                    boolean value = ((CompoundButton) view).isChecked();
                    pref.put(viewTag, ((CompoundButton) view).isChecked());
                    Log.v(TAG, viewTag + "=" + value);
                }
            }
        }
        Log.i(TAG, "Changes saved");
    }
}