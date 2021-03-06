package net.renoseven.framework;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashSet;
import java.util.Set;

/**
 * Extended Activity
 * Created by RenoSeven on 2016/10/23.
 */

public abstract class ExtendedActivity extends Activity {
    protected final String TAG;
    protected View rootView;

    public ExtendedActivity() {
        TAG = this.getClass().getSimpleName();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = findViewById(android.R.id.content);
    }

    /**
     * Function: getMetaValue
     * Params: String metaKey
     * Description: read meta data from AndroidManifest.xml
     * Return: String
     */
    @NonNull
    protected String getMetaValue(String metaKey) {
        String value = null;
        try {
            Bundle metaData = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA).metaData;
            value = metaData.getString(metaKey);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        if(value == null) {
            throw new RuntimeException("Cannot find Metadata");
        }
        return value;
    }

    /**
     * Function: findViewsByClass
     * Params: ViewGroup parent
     * Class<?> viewClass
     * Description: find views by specific class
     * Return: Set<View>
     */
    protected Set<View> findViewsByClass(Class<?> viewClass) {
        return findViewsByClass((ViewGroup) rootView, viewClass);
    }

    protected Set<View> findViewsByClass(ViewGroup parent, Class<?> viewClass) {
        Set<View> set = new HashSet<>();
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                set.addAll(findViewsByClass((ViewGroup) child, viewClass));
            } else if (viewClass.isAssignableFrom(child.getClass())) {
                set.add(child);
            }
        }
        return set;
    }

    /**
     * Function: findViewByTag
     * Params: ViewGroup parent
     * Class<?> viewClass
     * Description: find views by specific tag
     * Return: View
     */
    protected View findViewByTag(Object tag) {
        return rootView.findViewWithTag(tag);
    }
}
