package com.example.devices.network

import com.example.devices.model.Device
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("test/devices")
    suspend fun getAllDevices() : List<Device>

    @GET("test/devices")
    suspend fun getDevice(
        @Query("id") id: Int
    ) : Device

    @DELETE("test/devices")
    suspend fun deleteDevice(
        @Query("id") id: Int
    )


}