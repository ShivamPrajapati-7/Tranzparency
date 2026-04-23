package com.example.tranzparency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v = inflater.inflate(R.layout.fragment_profile, container, false)

        val swShake = v.findViewById<Switch>(R.id.swShake)
        val swVolume = v.findViewById<Switch>(R.id.swVolume)

        swShake.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(requireContext(), "Shake: ${if (isChecked) "On" else "Off"}", Toast.LENGTH_SHORT).show()
            // TODO: save in SharedPreferences
        }
        swVolume.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(requireContext(), "Volume+ combo: ${if (isChecked) "On" else "Off"}", Toast.LENGTH_SHORT).show()
        }

        return v
    }
}