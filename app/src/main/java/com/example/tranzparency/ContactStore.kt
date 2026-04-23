package com.example.tranzparency

import android.content.Context

class ContactStore(ctx: Context) {
    private val prefs = ctx.getSharedPreferences("contacts", Context.MODE_PRIVATE)

    fun getAll(): MutableList<String> {
        val set = prefs.getStringSet("list", emptySet()) ?: emptySet()
        return set.toMutableList().sorted().toMutableList()
    }

    fun add(number: String) {
        val clean = number.replace("\\s".toRegex(), "")
        val set = getAll().toMutableSet()
        set.add(clean)
        prefs.edit().putStringSet("list", set).apply()
    }

    fun remove(number: String) {
        val clean = number.replace("\\s".toRegex(), "")
        val set = getAll().toMutableSet()
        set.remove(clean)
        prefs.edit().putStringSet("list", set).apply()
    }

    fun removeAt(index: Int) {
        val list = getAll()
        if (index in list.indices) {
            list.removeAt(index)
            prefs.edit().putStringSet("list", list.toSet()).apply()
        }
    }

    fun clear() {
        prefs.edit().putStringSet("list", emptySet()).apply()
    }
}