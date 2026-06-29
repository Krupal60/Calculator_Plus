package com.plus.calculatorplus

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.KoinApplication
import org.koin.core.annotation.Module
import org.koin.plugin.module.dsl.startKoin

@KoinApplication
class CalculatorPlusApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin<CalculatorPlusAppModule> {
            androidContext(this@CalculatorPlusApp)
        }
    }
}

@Module
@ComponentScan("com.plus.calculatorplus")
class CalculatorPlusAppModule