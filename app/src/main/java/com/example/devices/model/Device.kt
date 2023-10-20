package com.example.devices.model

import kotlinx.serialization.Serializable

@Serializable
data class Device(
    val id: Int,
    val name: String,
    val icon: String,
    val isOnline: Boolean,
    val type: Int,
    val status: String,
    val lastWorkTime: Int,
)
