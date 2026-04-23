package com.example.tranzparency

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

class ShakeDetector(private val onShake: () -> Unit) : SensorEventListener {
    private var last = 0L

    override fun onSensorChanged(e: SensorEvent?) {
        if (e == null) return
        val g = sqrt(
            e.values[0]*e.values[0] +
                    e.values[1]*e.values[1] +
                    e.values[2]*e.values[2]
        ) / SensorManager.GRAVITY_EARTH

        if (g > 2.7f) {
            val now = System.currentTimeMillis()
            if (now - last > 1000) {
                last = now
                onShake()
            }
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}