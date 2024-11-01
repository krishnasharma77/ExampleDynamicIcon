package com.dynamicicon.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.JobIntentService

class MainActivityKotlin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        checkAndHandleIconChange()

//        change icon based on specific date
//        val intent = Intent(this, IconChangeService::class.java)
//        enqueueWork(this, intent)


    }

    override fun onDestroy() {
        super.onDestroy()
        //change icon based on specific date call this in destroy for perceived app crash
        val intent = Intent(this, IconChangeService::class.java)
        enqueueWork(this, intent)
    }

    companion object {
        private const val JOB_ID = 1001

        fun enqueueWork(context: Context, work: Intent) {
            JobIntentService.enqueueWork(context, IconChangeService::class.java, JOB_ID, work)
        }
    }
}
