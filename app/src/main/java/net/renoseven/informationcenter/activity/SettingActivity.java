package net.renoseven.informationcenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import net.grandcentrix.tray.TrayPreferences;
import net.renoseven.informationcenter.R;
import net.renoseven.informationcenter.preference.ApplicationPreferences;

import java.util.HashSet;
import java.util.Set;

public class SettingActivity extends AppCompatActivity {
    private final String TAG;
    private TrayPreferences appPreferences;

    private View smsForwardingSettings;
    private Switch smsForwardingEnable;

    private View mailForwardingSettings;
    private Switch mailForwardingEnable;

    public SettingActivity() {
        TAG = this.getClass().getSimpleName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Log.d(TAG, "Reading settings...");
        appPreferences = new ApplicationPreferences(this);

        Set<View> viewSet = findViews((ViewGroup) findViewById(R.id.scrollView));
        for (View view : viewSet) {
            String viewTag = (String) view.getTag();
            if (view instanceof EditText) {
                Log.d(TAG, viewTag + "=" + appPreferences.getString(viewTag, null));
                ((EditText) view).setText(appPreferences.getString(viewTag, null));
            } else if (view instanceof Switch) {
                Log.d(TAG, viewTag + "=" + appPreferences.getBoolean(viewTag, false));
                ((Switch) view).setChecked(appPreferences.getBoolean(viewTag, false));
            }
        }
        Log.i(TAG, "Settings loaded");

        initComponents();
    }

    private void initComponents() {
        // sms forwarding settings
        smsForwardingSettings = findViewById(R.id.layout_sms_forwarding_setting);
        smsForwardingEnable = (Switch) findViewById(R.id.sw_sms_forwarding_enable);
        smsForwardingEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateUI();
            }
        });

        // mail forwarding settings
        mailForwardingSettings = findViewById(R.id.layout_mail_forwarding_setting);
        mailForwardingEnable = (Switch) findViewById(R.id.sw_mail_forwarding_enable);
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

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Saving changes...");

        Set<View> viewSet = findViews((ViewGroup) findViewById(R.id.scrollView));
        for (View view : viewSet) {
            String viewTag = (String) view.getTag();
            if (view instanceof EditText) {
                appPreferences.put(viewTag, ((EditText) view).getText().toString());
                Log.d(TAG, viewTag + "=" + appPreferences.getString(viewTag, null));
            } else if (view instanceof Switch) {
                appPreferences.put(viewTag, ((Switch) view).isChecked());
                Log.d(TAG, viewTag + "=" + appPreferences.getBoolean(viewTag, false));
            }
        }
        Log.i(TAG, "Changes saved");

        super.onDestroy();
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