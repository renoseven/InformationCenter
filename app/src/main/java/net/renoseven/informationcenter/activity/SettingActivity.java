package net.renoseven.informationcenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;

import net.grandcentrix.tray.TrayPreferences;
import net.renoseven.informationcenter.R;
import net.renoseven.informationcenter.preference.ApplicationPreferences;

import java.util.HashSet;
import java.util.Set;

public class SettingActivity extends AppCompatActivity {
    private final String TAG;
    private TrayPreferences appPreferences;

    private View smsForwardingSettings;
    private CompoundButton smsForwardingEnable;

    private View mailForwardingSettings;
    private CompoundButton mailForwardingEnable;

    public SettingActivity() {
        TAG = this.getClass().getSimpleName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        appPreferences = new ApplicationPreferences(this);
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
        smsForwardingEnable = (CompoundButton) findViewById(R.id.sw_sms_forwarding_enable);
        smsForwardingEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateUI();
            }
        });

        // mail forwarding settings
        mailForwardingSettings = findViewById(R.id.layout_mail_forwarding_setting);
        mailForwardingEnable = (CompoundButton) findViewById(R.id.sw_mail_forwarding_enable);
        mailForwardingEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateUI();
            }
        });

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
        Set<View> viewSet = findViews((ViewGroup) findViewById(R.id.scrollView));
        for (View view : viewSet) {
            String viewTag = (String) view.getTag();
            if (view instanceof EditText) {
                Log.d(TAG, viewTag + "=" + appPreferences.getString(viewTag, null));
                ((EditText) view).setText(appPreferences.getString(viewTag, null));
            } else if (view instanceof CompoundButton) {
                Log.d(TAG, viewTag + "=" + appPreferences.getBoolean(viewTag, false));
                ((CompoundButton) view).setChecked(appPreferences.getBoolean(viewTag, false));
            }
        }
        Log.i(TAG, "Settings loaded");
    }

    private void saveSettings() {
        Log.d(TAG, "Saving changes...");

        Set<View> viewSet = findViews((ViewGroup) findViewById(R.id.scrollView));
        for (View view : viewSet) {
            String viewTag = (String) view.getTag();
            if (view instanceof EditText) {
                appPreferences.put(viewTag, ((EditText) view).getText().toString());
                Log.d(TAG, viewTag + "=" + appPreferences.getString(viewTag, null));
            } else if (view instanceof CompoundButton) {
                appPreferences.put(viewTag, ((CompoundButton) view).isChecked());
                Log.d(TAG, viewTag + "=" + appPreferences.getBoolean(viewTag, false));
            }
        }
        Log.i(TAG, "Changes saved");

    }

    private Set<View> findViews(ViewGroup g) {
        Set<View> set = new HashSet<>();
        for (int i = 0; i < g.getChildCount(); i++) {
            View view = g.getChildAt(i);
            if (view instanceof ViewGroup) {
                set.addAll(findViews((ViewGroup) view));
            } else {
                set.add(view);
            }
        }
        return set;
    }
}