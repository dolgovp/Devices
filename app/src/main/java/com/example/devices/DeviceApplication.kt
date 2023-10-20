package com.example.devices

import android.app.Application
import com.example.devices.network.AppContainer
import com.example.devices.network.DefaultAppContainer

class DeviceApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}