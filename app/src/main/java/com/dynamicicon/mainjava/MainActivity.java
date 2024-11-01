package com.dynamicicon.mainjava;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dynamicicon.screens.ChristmasAlias;
import com.dynamicicon.screens.NewYearAlias;
import com.dynamicicon.screens.R;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private boolean iconsChanged = false;
    private boolean iconsChangedOld = false;
    private boolean appRestarted = false;

    private static final String PREFERENCE_FILE_KEY = "my_preference_file";
    private static final String PREF_FIRST_OPEN_DATE = "firstOpenDate";
    private static final String PREF_ICON_CHANGED = "iconChanged";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAndHandleIconChange();

    }

    private void checkAndHandleIconChange() {
        try {
            Calendar calendar = Calendar.getInstance();
            int currentMonth = calendar.get(Calendar.MONTH) + 1;
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

            // Check if the app has been opened before
            SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
            int firstOpenDate = sharedPreferences.getInt(PREF_FIRST_OPEN_DATE, 0);

            if (firstOpenDate == 0) {
                // First time opening the app, record the current date
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(PREF_FIRST_OPEN_DATE, currentDay);
                editor.apply();
            }

            if ((currentMonth == Calendar.DECEMBER + 1) && (currentDay == 23) && (firstOpenDate == currentDay)) {
                // First open on the 25th of the month, apply new icon
                newicon();
            } else {
                // Apply old icon for other days or subsequent opens on the 25th
                oldicon();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void oldicon() {
        if (!isIconChanged()) {
            PackageManager packageManager = getPackageManager();
            packageManager.setComponentEnabledSetting(
                    new ComponentName(this, ChristmasAlias.class),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP
            );

            packageManager.setComponentEnabledSetting(
                    new ComponentName(this, NewYearAlias.class),
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP
            );

            Toast.makeText(getApplicationContext(), "Launcher old has been applied successfully", Toast.LENGTH_SHORT).show();
            setIconChanged(true);
            restartAppWithDelay();
        }
    }

    void newicon() {
        if (!isIconChanged()) {
            PackageManager packageManager = getPackageManager();
            packageManager.setComponentEnabledSetting(
                    new ComponentName(this, NewYearAlias.class),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP
            );

            packageManager.setComponentEnabledSetting(
                    new ComponentName(this, ChristmasAlias.class),
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP
            );

            Toast.makeText(getApplicationContext(), "Launcher new has been applied successfully", Toast.LENGTH_SHORT).show();

            setIconChanged(true);
            restartAppWithDelay();
        }
    }

    private boolean isIconChanged() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(PREF_ICON_CHANGED, false);
    }

    void setIconChanged(boolean changed) {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_ICON_CHANGED, changed);
        editor.apply();
    }

    void restartAppWithDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Restart the app
                Intent intent = getApplication().getPackageManager()
                        .getLaunchIntentForPackage(getApplication().getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }, 1000); // Delay of 1 second (adjust as needed)
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}

// dummy code
   /* private void checkAndHandleIconChange() {
        try {
            Calendar calendar = Calendar.getInstance();
            int currentMonth = calendar.get(Calendar.MONTH) + 1;
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

            if ((currentMonth == Calendar.NOVEMBER + 1) && (currentDay == 25 || currentDay == 26)) {
                if (!isIconChanged()) {
                    if (currentDay == 25) {
                        newicon();
                    } else {
                        oldicon();
                    }
                }
            } else {
                // Reset the iconsChanged flag if the current date is not November 25 or 26
                setIconChanged(false, "iconChanged");
                setIconChanged(false, "oldChanged");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void oldicon() {
        PackageManager packageManager = getPackageManager();
        packageManager.setComponentEnabledSetting(
                new ComponentName(this, OldLauncherAlias.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
        );

        packageManager.setComponentEnabledSetting(
                new ComponentName(this, OneLauncherAlias.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
        );

        Toast.makeText(getApplicationContext(), "Launcher old has been applied successfully", Toast.LENGTH_SHORT).show();
        setIconChanged(true, "oldChanged");
        restartAppWithDelay();
    }

    void newicon() {
        PackageManager packageManager = getPackageManager();
        packageManager.setComponentEnabledSetting(
                new ComponentName(this, OneLauncherAlias.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
        );

        packageManager.setComponentEnabledSetting(
                new ComponentName(this, OldLauncherAlias.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
        );

        Toast.makeText(getApplicationContext(), "Launcher new has been applied successfully", Toast.LENGTH_SHORT).show();
        setIconChanged(true, "iconChanged");
        setIconChanged(false, "oldChanged");  // Reset oldChanged flag
        restartAppWithDelay();
    }

    private boolean isIconChanged() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("iconChanged", false);
    }

    void setIconChanged(boolean changed, String key) {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, changed);
        editor.apply();
    }

    void restartAppWithDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Restart the app
                Intent intent = getApplication().getPackageManager()
                        .getLaunchIntentForPackage(getApplication().getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }, 1000); // Delay of 1 second (adjust as needed)
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }*/

       /* try {
            Calendar calendar = Calendar.getInstance();
            int currentMonth = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            int currentMinute = calendar.get(Calendar.MINUTE);

            if ((currentMonth == Calendar.NOVEMBER + 1) && (currentDay == 25 || currentDay == 26)) {
                if (!iconsChanged) {
                    if (currentDay == 25) {
                        newicon();
                    } else {
                        oldicon();
                    }
                    if (!appRestarted) {
                        restartAppWithDelay();
                        appRestarted = true;
                    }
                    iconsChanged = true;
//                    restartAppWithDelay();
                }
            } else {
                // Reset the iconsChanged flag if the current date is not November 22, 23, or 24
                appRestarted = false;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

       /*
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);*/

/*
        try {
            //if (month == Calendar.NOVEMBER && day == 21) {
            if (month == Calendar.NOVEMBER && day == 22 && hour == 15 && minute == 22) {
                if (!iconsChanged) {
                    newicon();
                    iconsChanged = true;
                }
            } else {
                if (!iconsChanged) {
                    oldicon();
                    iconsChanged = true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

