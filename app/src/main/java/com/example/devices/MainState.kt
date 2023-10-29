package com.example.devices

import com.example.devices.model.Device
import com.example.devices.model.Devices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class MainState(
    val uiState: DeviceUiState,
    val devices: MutableList<Device> = mutableListOf()
)
