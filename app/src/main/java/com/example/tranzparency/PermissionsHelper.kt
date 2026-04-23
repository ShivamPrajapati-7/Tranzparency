package com.example.tranzparency

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionsHelper {
    private val p = arrayOf(
        Manifest.permission.SEND_SMS,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    fun hasAll(a: Activity) =
        p.all { ContextCompat.checkSelfPermission(a, it) == PackageManager.PERMISSION_GRANTED }

    fun requestAll(a: Activity) {
        ActivityCompat.requestPermissions(a, p, 1)
    }
}