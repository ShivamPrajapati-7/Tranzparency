package com.example.tranzparency

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager

class LocationHelper(private val ctx: Context) {
    @SuppressLint("MissingPermission")
    fun fetch(cb: (Location?) -> Unit) {
        val lm = ctx.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        cb(lm.getLastKnownLocation(LocationManager.GPS_PROVIDER))
    }
}