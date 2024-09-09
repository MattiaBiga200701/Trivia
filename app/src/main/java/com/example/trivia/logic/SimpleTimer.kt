package com.example.trivia.logic

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.util.Locale

class SimpleTimer(private val updateInterval: Long = 1000L) {

    private var startTime: Long = 0
    private var elapsedTime: Long = 0
    private var isRunning = false
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                elapsedTime = System.currentTimeMillis() - startTime
                handler.postDelayed(this, updateInterval)  // Ricontrolla fra updateInterval ms
            }
        }
    }


    fun start() {
        if (!isRunning && getElapsedTimeFormatted() == "00:00") {
            startTime = System.currentTimeMillis() - elapsedTime
            isRunning = true
            handler.post(runnable)

        } else {
            Log.d("SimpleTimer", "Timer start failed: Timer is already running or not at 00:00")
        }
    }


    fun stop() {
        if (isRunning) {
            isRunning = false
            handler.removeCallbacks(runnable)

        }
    }


    fun reset() {
        stop()
        elapsedTime = 0
        startTime = System.currentTimeMillis()

    }


    private fun getElapsedTime(): Long {
        return if (isRunning) {
            System.currentTimeMillis() - startTime
        } else {
            elapsedTime
        }
    }

    fun getElapsedTimeFormatted(): String {
        val elapsedMillis = getElapsedTime()
        val seconds = (elapsedMillis / 1000) % 60
        val minutes = (elapsedMillis / 1000) / 60
        return String.format(Locale.US , "%02d:%02d", minutes, seconds)
    }
}
