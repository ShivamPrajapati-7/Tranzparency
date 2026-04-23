package com.example.tranzparency

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Locale

class ArchiveStore(ctx: Context) {
    private val prefs = ctx.getSharedPreferences("sos_archive", Context.MODE_PRIVATE)
    private val KEY = "logs"

    fun add(line: String) {
        val all = getAll().toMutableList()
        all.add(line)
        prefs.edit().putString(KEY, all.joinToString("\n")).apply()
    }

    fun getAll(): List<String> =
        (prefs.getString(KEY, "") ?: "").split("\n").filter { it.isNotBlank() }

    companion object {
        fun stamp(): String =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(System.currentTimeMillis())
    }
}
