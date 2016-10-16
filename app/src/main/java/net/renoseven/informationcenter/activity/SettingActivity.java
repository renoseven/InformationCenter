package net.renoseven.informationcenter.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import net.renoseven.informationcenter.R;

import java.util.HashSet;
import java.util.Set;

public class SettingActivity extends AppCompatActivity {
    private final String TAG;
    private SharedPreferences applicationPref;

    public  SettingActivity() {
        TAG = this.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Log.d(TAG, "Reading settings...");
        applicationPref = getSharedPreferences("app", MODE_PRIVATE);

        Set<View> viewSet = findViews((ViewGroup) findViewById(R.id.scrollView));
        for(View view : viewSet) {
            String viewTag = (String) view.getTag();
            if (view instanceof EditText) {
                ((EditText) view).setText(applicationPref.getString(viewTag, null));
            }
            else if (view instanceof Switch) {
                ((Switch) view).setChecked(Boolean.valueOf(applicationPref.getString(viewTag, null)));
            }
        }
        Log.d(TAG, "Settings loaded");
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Saving changes...");
        SharedPreferences.Editor appEditor = applicationPref.edit();

        Set<View> viewSet = findViews((ViewGroup) findViewById(R.id.scrollView));
        for(View view : viewSet) {
            String viewTag = (String) view.getTag();
            if (view instanceof EditText) {
                appEditor.putString(viewTag, ((EditText) view).getText().toString());
            }
            else if (view instanceof Switch) {
                appEditor.putString(viewTag, String.valueOf(((Switch) view).isChecked()));
            }
        }
        appEditor.apply();
        Log.d(TAG, "Changes saved");

        super.onDestroy();
    }

    private Set<View> findViews(ViewGroup g) {
        Set<View> set = new HashSet<>();
        for(int i = 0; i < g.getChildCount(); i ++) {
            View view = g.getChildAt(i);
            if (view instanceof ViewGroup) {
                set.addAll(findViews((ViewGroup) view));
            }
            else {
                set.add(view);
            }
        }
        return set;
    }
}