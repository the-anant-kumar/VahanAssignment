package com.anant.myvahan

import android.app.Application
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder

class RefreshService : Service() {
    private val handler = Handler()
    private lateinit var runnable: Runnable

    override fun onCreate() {
        super.onCreate()
        // Initialize your runnable for fetching data here
        runnable = Runnable {
            // Implement the logic to fetch data from the API
            val mainActivity =
            // You can call fetchUniversityData() from here
            // Schedule the next execution of this runnable
            handler.postDelayed(runnable, 10000) // 10 seconds delay
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Start the runnable
        handler.post(runnable)
        // Return START_STICKY to ensure the service restarts if it's killed
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove any callbacks to prevent memory leaks
        handler.removeCallbacks(runnable)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
