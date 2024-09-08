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
                // Aggiorna il tempo solo se il timer è in esecuzione
                elapsedTime = System.currentTimeMillis() - startTime
                handler.postDelayed(this, updateInterval)  // Ricontrolla fra updateInterval ms
            }
        }
    }

    // Start the timer
    fun start() {
        if (!isRunning && getElapsedTimeFormatted() == "00:00") {
            startTime = System.currentTimeMillis() - elapsedTime
            isRunning = true
            handler.post(runnable)
            Log.d("SimpleTimer", "Timer started")
        } else {
            Log.d("SimpleTimer", "Timer start failed: Timer is already running or not at 00:00")
        }
    }

    // Stop the timer
    fun stop() {
        if (isRunning) {
            isRunning = false  // Imposta isRunning a false
            handler.removeCallbacks(runnable)  // Rimuovi i callback in attesa
            Log.d("SimpleTimer", "Timer stopped")
        }
    }

    // Reset the timer
    fun reset() {
        stop()  // Ferma il timer prima di resettare
        elapsedTime = 0
        startTime = System.currentTimeMillis()
        Log.d("SimpleTimer", "Timer reset")
    }

    // Get elapsed time in milliseconds
    private fun getElapsedTime(): Long {
        return if (isRunning) {
            System.currentTimeMillis() - startTime
        } else {
            elapsedTime
        }
    }

    // Convert elapsed time to mm:ss format
    fun getElapsedTimeFormatted(): String {
        val elapsedMillis = getElapsedTime()
        val seconds = (elapsedMillis / 1000) % 60
        val minutes = (elapsedMillis / 1000) / 60
        return String.format(Locale.US , "%02d:%02d", minutes, seconds)
    }
}
