package com.trios2025dj.contactlist

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ContactStorage {
    private const val PREFS_NAME = "contact_prefs"
    private const val KEY_CONTACTS = "contacts"

    private val gson = Gson()
    private val type = object : TypeToken<MutableList<Contact>>() {}.type

    fun saveContacts(context: Context, contacts: MutableList<Contact>) {
        val json = gson.toJson(contacts)
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_CONTACTS, json).apply()
    }

    fun loadContacts(context: Context): MutableList<Contact> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_CONTACTS, null)
        return if (json != null) {
            gson.fromJson(json, type)
        } else {
            mutableListOf()
        }
    }
}
