package com.plus.calculatorplus

import android.app.Application

class Calculator : Application() {
    override fun onCreate() {
        super.onCreate()
        calculator = this
    }
    companion object{
        var calculator : Calculator? = null
        private set
    }
}