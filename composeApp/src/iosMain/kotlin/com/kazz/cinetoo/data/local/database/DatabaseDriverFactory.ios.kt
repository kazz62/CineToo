package com.kazz.cinetoo.data.local.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.kazz.cinetoo.database.CineTooDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(CineTooDatabase.Schema, "cinetoo.db")
    }
}
