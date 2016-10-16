package net.renoseven.informationcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.renoseven.framework.NIAActivity;
import net.renoseven.informationcenter.R;
import net.renoseven.informationcenter.service.InformationService;

public class MainActivity extends NIAActivity {
    private Button btnStartService;
    private Button btnStopService;
    private Button btnSettings;
    private TextView txtServiceState;

    @Override
    protected String getServiceClassName() {
        return InformationService.class.getName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        btnStartService = (Button) findViewById(R.id.btn_start_service);
        btnStopService = (Button) findViewById(R.id.btn_stop_service);
        btnSettings = (Button) findViewById(R.id.btn_settings);
        txtServiceState = (TextView) findViewById(R.id.txt_service_state);

        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnStartServiceClick();
            }
        });
        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnStopServiceClick();
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnSettingsClick();
            }
        });

        onServiceDead();
    }

    private void onBtnStartServiceClick() {
        Intent startIntent = new Intent(this, InformationService.class);
        startService(startIntent);
    }

    private void onBtnStopServiceClick() {
        stopService();
    }

    private void onBtnSettingsClick() {
        startActivity(new Intent(this, SettingActivity.class));
    }

    @Override
    public void onServiceBorn() {
        btnStartService.setEnabled(false);
        btnStopService.setEnabled(true);
        btnSettings.setEnabled(false);
        txtServiceState.setText(R.string.service_state_up);
    }

    @Override
    public void onServiceDead() {
        btnStartService.setEnabled(true);
        btnStopService.setEnabled(false);
        btnSettings.setEnabled(true);
        txtServiceState.setText(R.string.service_state_down);
    }

    @Override
    public void onServiceAlive() {
        onServiceBorn();
    }
}
