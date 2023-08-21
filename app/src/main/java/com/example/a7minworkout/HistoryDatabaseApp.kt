package com.example.a7minworkout

import android.app.Application

class HistoryDatabaseApp: Application() {
    val db by lazy {
        HistoryDatabase.getDatabaseInstance(this)
    }
}