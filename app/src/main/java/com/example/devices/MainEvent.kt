package com.example.devices

interface MainEvent

class GetDevicesEvent() : MainEvent

class DeleteEvent(val id: Int) : MainEvent

class ResetEvent() : MainEvent
