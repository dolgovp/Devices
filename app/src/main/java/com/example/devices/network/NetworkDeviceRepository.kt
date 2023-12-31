package com.example.devices.network

import com.example.devices.model.Device
import com.example.devices.model.Devices
import retrofit2.Response

class NetworkDeviceRepository(
    private val apiService: ApiService
) : DeviceRepository {
    override suspend fun getAllDevices(): Devices = apiService.getAllDevices()

    override suspend fun getDevice(id: Int): Device = apiService.getDevice(id)

    override suspend fun deleteDevice(id: Int) : Response<Unit> = apiService.deleteDevice(id)
    override suspend fun reset(): Response<Unit> = apiService.reset()

}