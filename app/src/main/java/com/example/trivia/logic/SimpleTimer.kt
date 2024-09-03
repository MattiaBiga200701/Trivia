package com.example.trivia.logic

import android.os.Handler
import android.os.Looper
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
                handler.postDelayed(this, updateInterval)
            }
        }
    }

    // Start the timer
    fun start() {
        if (!isRunning) {
            startTime = System.currentTimeMillis() - elapsedTime
            isRunning = true
            handler.post(runnable)
        }
    }

    // Stop the timer
    fun stop() {
        if (isRunning) {
            isRunning = false
            handler.removeCallbacks(runnable)
        }
    }

    // Reset the timer
    fun reset() {
        stop()
        elapsedTime = 0
        startTime = System.currentTimeMillis()
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