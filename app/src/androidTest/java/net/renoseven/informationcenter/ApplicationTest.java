package net.renoseven.informationcenter;

import android.app.Application;
import android.content.ContentResolver;
import android.net.Uri;
import android.test.ApplicationTestCase;
import android.util.Log;

import dalvik.annotation.TestTarget;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }
}
