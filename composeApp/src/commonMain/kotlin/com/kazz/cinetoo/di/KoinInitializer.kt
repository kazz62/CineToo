package com.kazz.cinetoo.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(
            platformModule(),
            dataModule(),
            domainModule(),
            presentationModule()
        )
    }
}

fun initKoin(modules: List<Module>) {
    startKoin {
        modules(modules)
    }
}
