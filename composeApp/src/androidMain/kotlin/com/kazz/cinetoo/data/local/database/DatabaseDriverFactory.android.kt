package com.kazz.cinetoo.data.local.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.kazz.cinetoo.database.CineTooDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(CineTooDatabase.Schema, context, "cinetoo.db")
    }
}
