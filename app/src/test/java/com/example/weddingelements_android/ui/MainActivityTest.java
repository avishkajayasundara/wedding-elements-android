package com.example.weddingelements_android.ui;

import android.content.Intent;
import android.widget.Button;

import com.example.weddingelements_android.R;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowActivity;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest extends TestCase {
    private MainActivity activity;

    public void setUp() throws Exception {
        super.setUp();
        activity = Robolectric.buildActivity( MainActivity.class )
                .create()
                .resume()
                .get();
    }

    public void tearDown() throws Exception {

    }

    @Test
    public void shouldNotBeNull() throws Exception
    {
        assertNotNull( activity );
    }

    @Test
    public void checkButtonNullability() {
        Button loginBtn = activity.findViewById(R.id.main_login);
        Button regBtn = activity.findViewById(R.id.main_register);
        assertNotNull("Login Button Cannot Be Found", loginBtn);
        assertNotNull("Register Button Cannot Be Found", regBtn);
    }

    @Test
    public void checkLoginActivityNavigation(){
        activity.findViewById(R.id.main_login).performClick();
        Intent expectedIntent = new Intent(activity, LoginActivity.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));

    }

    @Test
    public void checkRegisterActivityNavigation(){
        activity.findViewById(R.id.main_login).performClick();
        Intent expectedIntent = new Intent(activity, LoginActivity.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }


}