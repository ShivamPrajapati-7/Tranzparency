package com.example.tranzparency

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tranzparency.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var sms: SMSHelper
    private lateinit var contacts: ContactStore
    private lateinit var location: LocationHelper
    private lateinit var sensorManager: SensorManager
    private lateinit var shakeDetector: ShakeDetector

    private var shakeDetected = false
    private var volumeUpPressed = false

    private val prefs by lazy {
        getSharedPreferences("sos_history", Context.MODE_PRIVATE)
    }

    private val circleFragment by lazy { CircleFragment() }
    private val exploreFragment by lazy { ExploreFragment() }
    private val profileFragment by lazy { ProfileFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // FIXED
        sms = SMSHelper()
        contacts = ContactStore(this)
        location = LocationHelper(this)

        PermissionsHelper.requestAll(this)

        // ✅ Bottom navigation (NO .root)
        binding.navHome.setOnClickListener { showHome() }
        binding.navCircle.setOnClickListener { showFragment(circleFragment) }
        binding.navExplore.setOnClickListener { showFragment(exploreFragment) }
        binding.navProfile.setOnClickListener { showFragment(profileFragment) }

        showHome()

        // SOS
        binding.btnSOS.setOnClickListener {
            val (ok, msg) = isDeviceReady()
            if (!ok) {
                AlertDialog.Builder(this)
                    .setTitle("Device Not Ready")
                    .setMessage(msg)
                    .setPositiveButton("OK", null)
                    .show()
                return@setOnClickListener
            }
            triggerSOS()
        }

        binding.btnCamera.setOnClickListener {
            Toast.makeText(this, "Add contacts here", Toast.LENGTH_SHORT).show()
        }

        binding.btnBell.setOnClickListener {
            showHistoryDialog()
        }

        // Shake
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        shakeDetector = ShakeDetector {
            shakeDetected = true
            Toast.makeText(this, "Shake detected → press volume up", Toast.LENGTH_SHORT).show()
        }

        sensorManager.registerListener(shakeDetector, accel, SensorManager.SENSOR_DELAY_UI)
    }

    private fun showHome() {
        binding.fragmentContainer.visibility = View.GONE
        binding.homeContainer.visibility = View.VISIBLE
    }

    private fun showFragment(f: Fragment) {
        binding.homeContainer.visibility = View.GONE
        binding.fragmentContainer.visibility = View.VISIBLE

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, f)
            .commit()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            volumeUpPressed = true

            if (shakeDetected && volumeUpPressed) {
                shakeDetected = false
                volumeUpPressed = false
                triggerSOS()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(shakeDetector)
    }

    private fun triggerSOS() {
        val nums = contacts.getAll()

        if (nums.isEmpty()) {
            Toast.makeText(this, "No contacts saved", Toast.LENGTH_LONG).show()
            return
        }

        location.fetch { loc ->
            val link = if (loc != null)
                "https://maps.google.com/?q=${loc.latitude},${loc.longitude}"
            else "Location unavailable"

            val msg = "SOS! I need help. Location: $link"

            nums.forEach {
                try { sms.send(it, msg) } catch (_: Exception) {}
            }

            appendHistory(msg)

            Toast.makeText(this, "SOS sent", Toast.LENGTH_LONG).show()
        }
    }

    private fun isDeviceReady(): Pair<Boolean, String> {
        val reasons = mutableListOf<String>()

        val lm = getSystemService(LOCATION_SERVICE) as android.location.LocationManager
        val gpsOn = lm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)

        if (!gpsOn) reasons += "Enable GPS"

        val tm = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        if (tm.simState != TelephonyManager.SIM_STATE_READY)
            reasons += "Insert SIM"

        return if (reasons.isEmpty()) true to "" else false to reasons.joinToString("\n")
    }

    private fun showHistoryDialog() {
        val list = getHistoryList()

        val text = if (list.isEmpty()) "No history"
        else list.joinToString("\n")

        AlertDialog.Builder(this)
            .setTitle("SOS History")
            .setMessage(text)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun appendHistory(text: String) {
        val old = prefs.getString("list", "") ?: ""
        prefs.edit().putString("list",
            if (old.isEmpty()) text else "$old\n$text"
        ).apply()
    }

    private fun getHistoryList(): List<String> {
        val raw = prefs.getString("list", "") ?: ""
        return if (raw.isEmpty()) emptyList() else raw.split("\n")
    }
}