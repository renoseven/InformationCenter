package net.renoseven.util;

import android.content.Context;
import android.widget.Toast;

public abstract class ToastUtil {
    private static Toast showingToast;

    public static void showToast(final Context context, final String tips) {
        showToast(context, tips, Toast.LENGTH_SHORT);
    }

    public static void showToast(final Context context, final int tips) {
        showToast(context, tips, Toast.LENGTH_SHORT);
    }

    public static void showToast(final Context context, final String tips, final int duration) {
        if (android.text.TextUtils.isEmpty(tips)) {
            return;
        }
        if (showingToast != null) {
            showingToast.cancel();
        }
        showingToast = Toast.makeText(context, tips, duration);
        showingToast.show();
    }

    public static void showToast(final Context context, final int tips, final int duration) {
        if (showingToast != null) {
            showingToast.cancel();
        }
        showingToast = Toast.makeText(context, tips, duration);
        showingToast.show();
    }
}