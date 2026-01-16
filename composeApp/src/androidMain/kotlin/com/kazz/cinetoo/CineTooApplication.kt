package com.kazz.cinetoo

import android.app.Application
import com.kazz.cinetoo.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class CineTooApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@CineTooApplication)
            properties(
                mapOf("TMDB_API_KEY" to BuildConfig.TMDB_API_KEY)
            )
        }
    }
}
