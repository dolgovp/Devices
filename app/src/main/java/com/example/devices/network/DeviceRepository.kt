package com.example.devices.network

import com.example.devices.model.Device

interface DeviceRepository {
    suspend fun getAllDevices() : List<Device>
    suspend fun getDevice(id: Int) : Device
    suspend fun deleteDevice(id: Int)
}