package com.kazz.cinetoo.di

import app.cash.sqldelight.db.SqlDriver
import com.kazz.cinetoo.data.local.database.DatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<SqlDriver> { DatabaseDriverFactory().createDriver() }
}
