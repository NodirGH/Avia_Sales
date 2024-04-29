package com.example.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object AppPreferences {

    const val AVIA_SALES = "avia_sales"

    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(AVIA_SALES, MODE_PRIVATE)
    }
}