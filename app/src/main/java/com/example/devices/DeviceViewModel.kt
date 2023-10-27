package com.example.devices

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.devices.model.Devices
import com.example.devices.network.DeviceRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface DeviceUiState {
    data class Loaded(val devices: Devices) : DeviceUiState
    object Error : DeviceUiState
    object Loading : DeviceUiState
}
class DeviceViewModel(private val deviceRepository: DeviceRepository) : ViewModel() {

    private val _deviceUiState = MutableStateFlow<DeviceUiState>(DeviceUiState.Loading)
    val deviceUiState: StateFlow<DeviceUiState> = _deviceUiState

    init {
        getDevices()
    }
    fun getDevices(){
        viewModelScope.launch{
            _deviceUiState.value = DeviceUiState.Loading
            _deviceUiState.value = try {
                DeviceUiState.Loaded(deviceRepository.getAllDevices())
            } catch (e: HttpException){
                Log.d("error", e.toString())
                DeviceUiState.Error
            } catch (e: IOException) {
                DeviceUiState.Error
            }
        }
    }

    fun deleteDevice(id: Int){
        viewModelScope.launch {
            try{
                deviceRepository.deleteDevice(id)
                Log.d("$id", "DELETED")
            } catch (e: HttpException){
                Log.d("error $id", e.toString())
            }
            getDevices()
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DeviceApplication)
                val deviceRepository = application.container.deviceRepository
                DeviceViewModel(deviceRepository = deviceRepository)
            }
        }
    }
}