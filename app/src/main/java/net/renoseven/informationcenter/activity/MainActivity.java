package net.renoseven.informationcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.grandcentrix.tray.TrayPreferences;
import net.grandcentrix.tray.core.OnTrayPreferenceChangeListener;
import net.grandcentrix.tray.core.TrayItem;
import net.renoseven.framework.nias.NIAActivity;
import net.renoseven.informationcenter.R;
import net.renoseven.informationcenter.preference.StatisticsPreferences;
import net.renoseven.informationcenter.service.InformationService;

import java.util.Collection;
import java.util.Set;

public class MainActivity extends NIAActivity implements View.OnClickListener, OnTrayPreferenceChangeListener {
    private TrayPreferences statPreferences;

    private Button btnStartService;
    private Button btnStopService;
    private Button btnSettings;
    private TextView txtServiceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statPreferences = new StatisticsPreferences(this);
        statPreferences.registerOnTrayPreferenceChangeListener(this);

        loadSettings();

        btnStartService = (Button) findViewById(R.id.btn_start_service);
        btnStopService = (Button) findViewById(R.id.btn_stop_service);
        btnSettings = (Button) findViewById(R.id.btn_settings);
        txtServiceState = (TextView) findViewById(R.id.txt_service_state);

        btnStartService.setOnClickListener(this);
        btnStopService.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        statPreferences.unregisterOnTrayPreferenceChangeListener(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnStartService)) {
            Intent startIntent = new Intent(this, InformationService.class);
            startService(startIntent);
        }
        if (v.equals(btnStopService)) {
            stopService();
        }
        if (v.equals(btnSettings)) {
            startActivity(new Intent(this, SettingActivity.class));
        }
    }

    @Override
    public void onTrayPreferenceChanged(Collection<TrayItem> items) {
        Log.d(TAG, "Statistics changed");
        for (TrayItem item : items) {
            String key = item.key();
            String value = item.value();
            Log.v(TAG, key + "=" + value);
            TextView text = (TextView) findViewByTag(key);
            if (text != null) {
                text.setText(value);
            }
        }
    }

    @Override
    protected void updateUI(@Nullable Bundle reply) {
        if (isServiceAlive()) {
            btnStartService.setEnabled(false);
            btnStopService.setEnabled(true);
            btnSettings.setEnabled(false);
            txtServiceState.setText(R.string.service_state_up);
        } else {
            btnStartService.setEnabled(true);
            btnStopService.setEnabled(false);
            btnSettings.setEnabled(true);
            txtServiceState.setText(R.string.service_state_down);
        }
        Log.d(TAG, "UI updated");
    }

    private void loadSettings() {
        Log.d(TAG, "Reading settings...");
        Set<View> textSet = findViewsByClass(TextView.class);
        for (View view : textSet) {
            String viewTag = (String) view.getTag();
            if (viewTag != null) {
                String value = statPreferences.getString(viewTag, null);
                ((TextView) view).setText(value);
                Log.v(TAG, viewTag + "=" + value);
            }
        }
        Log.i(TAG, "Settings loaded");
    }
}