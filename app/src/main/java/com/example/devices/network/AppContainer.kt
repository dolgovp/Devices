package com.example.devices.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer{
    val deviceRepository: DeviceRepository
}
class DefaultAppContainer : AppContainer{
    private val BASE_FASTHOME_URL = "https://api.fasthome.io/"

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BASE_FASTHOME_URL)
        .build()

    private val retrofitService: ApiService by lazy{
        retrofit.create(ApiService::class.java)
    }

    override val deviceRepository: DeviceRepository by lazy {
        NetworkDeviceRepository(retrofitService)
    }


}