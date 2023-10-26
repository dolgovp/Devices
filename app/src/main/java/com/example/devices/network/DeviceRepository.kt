package com.example.devices.network

import com.example.devices.model.Device
import com.example.devices.model.Devices

interface DeviceRepository {
    suspend fun getAllDevices() : Devices
    suspend fun getDevice(id: Int) : Device
    suspend fun deleteDevice(id: Int)
}
