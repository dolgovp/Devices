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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface DeviceUiState {
    object Loaded : DeviceUiState
    data class Error(val error: Exception) : DeviceUiState
    object Loading : DeviceUiState
}
class DeviceViewModel(private val deviceRepository: DeviceRepository) : ViewModel() {

    private val _deviceUiState = MutableStateFlow(MainState(DeviceUiState.Loading))
    val deviceUiState: StateFlow<MainState> = _deviceUiState



    init {
        send(ResetEvent())
        send(GetDevicesEvent())
    }

    fun send(event: MainEvent){
        when(event){
            is GetDevicesEvent -> getDevices()
            is DeleteEvent -> deleteDevice(event.id)
            is ResetEvent -> reset()
        }

    }

    private fun reset(){
        viewModelScope.launch {
            _deviceUiState.value = MainState(DeviceUiState.Loading)
            _deviceUiState.value = try{
                deviceRepository.reset()
                MainState(DeviceUiState.Loading)
            } catch (e: HttpException){
                MainState(DeviceUiState.Error(e))
            } catch (e: IOException) {
                MainState(DeviceUiState.Error(e))
            }
        }

    }
    private fun getDevices(){
        viewModelScope.launch{
            _deviceUiState.value = MainState(DeviceUiState.Loading)
            _deviceUiState.value = try {
                MainState(DeviceUiState.Loaded, deviceRepository.getAllDevices().data)
            } catch (e: HttpException){
                Log.d("error", e.toString())
                MainState(DeviceUiState.Error(e))
            } catch (e: IOException) {
                MainState(DeviceUiState.Error(e))
            }
        }
    }

    private fun deleteDevice(id: Int){
        viewModelScope.launch {
            _deviceUiState.value = try{
                deviceRepository.deleteDevice(id)
                //_deviceUiState.value.devices.removeIf{it.id == id}
                getDevices()
                MainState(DeviceUiState.Loaded, _deviceUiState.value.devices)
            } catch (e: HttpException){
                MainState(DeviceUiState.Error(e))
            } catch (e: IOException) {
                MainState(DeviceUiState.Error(e))
            }
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