package com.dynamicicon.screens

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.JobIntentService
import java.util.Calendar

// ...

class IconChangeService : JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        // Perform icon change here
        val calendar = Calendar.getInstance()
        val currentMonth = calendar[Calendar.MONTH] + 1
        val currentDay = calendar[Calendar.DAY_OF_MONTH]

        if (currentMonth == Calendar.NOVEMBER + 1 && currentDay == 1) {
            changeIconToChristmas(applicationContext)
        } else if (currentMonth == Calendar.DECEMBER + 1 && currentDay == 26) {
            changeIconToNewYear(applicationContext)
        } else {
            changeIconToMain(applicationContext)
        }
    }

    private fun changeIconToMain(context: Context) {
        try {
            val packageManager = context.packageManager
            val newYearComponent = ComponentName(context, "${context.packageName}.NewYearAlias")
            packageManager.setComponentEnabledSetting(
                newYearComponent,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
            val christmasComponent = ComponentName(context, "${context.packageName}.ChristmasAlias")
            packageManager.setComponentEnabledSetting(
                christmasComponent,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
            val mainActivityComponent =
                ComponentName(context, "com.dynamicicon.screens.MainActivityKotlin")
            packageManager.setComponentEnabledSetting(
                mainActivityComponent,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun changeIconToNewYear(context: Context) {
        try {
            val packageManager = context.packageManager
            val firstLauncherComponent =
                ComponentName(context, "com.dynamicicon.screens.MainActivityKotlin")
            packageManager.setComponentEnabledSetting(
                firstLauncherComponent,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
            val christmasComponent = ComponentName(context, "${context.packageName}.ChristmasAlias")
            packageManager.setComponentEnabledSetting(
                christmasComponent,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
            val secondLauncherComponent =
                ComponentName(context, "${context.packageName}.NewYearAlias")
            packageManager.setComponentEnabledSetting(
                secondLauncherComponent,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun changeIconToChristmas(context: Context) {
        try {
            val packageManager = context.packageManager
            val mainActivityComponent =
                ComponentName(context, "com.dynamicicon.screens.MainActivityKotlin")
            packageManager.setComponentEnabledSetting(
                mainActivityComponent,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
            val newYearComponent = ComponentName(context, "${context.packageName}.NewYearAlias")
            packageManager.setComponentEnabledSetting(
                newYearComponent,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
            val christmasComponent = ComponentName(context, "${context.packageName}.ChristmasAlias")
            packageManager.setComponentEnabledSetting(
                christmasComponent,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }


}
