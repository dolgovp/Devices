package com.example.devices.network

import com.example.devices.model.Device
import com.example.devices.model.Devices
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/v1/test/devices")
    suspend fun getAllDevices() : Devices

    @GET("api/v1/test/devices")
    suspend fun getDevice(
        @Query("id") id: Int
    ) : Device

    @DELETE("api/v1/test/devices")
    suspend fun deleteDevice(
        @Query("id") id: Int
    )


}