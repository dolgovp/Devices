package com.example.devices.network

import com.example.devices.model.Device

class NetworkDeviceRepository(
    private val apiService: ApiService
) : DeviceRepository {
    override suspend fun getAllDevices(): List<Device> = apiService.getAllDevices()

    override suspend fun getDevice(id: Int): Device = apiService.getDevice(id)

    override suspend fun deleteDevice(id: Int)  = apiService.deleteDevice(id)

}