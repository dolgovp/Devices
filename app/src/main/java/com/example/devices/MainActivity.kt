package com.example.devices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devices.ui.theme.DevicesTheme
import com.example.devices.ui.theme.PurpleText


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevicesTheme {
                val deviceViewModel: DeviceViewModel =
                    viewModel(factory = DeviceViewModel.Factory)
                val state = deviceViewModel.deviceUiState.collectAsState().value
                Scaffold(

                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        when(state.uiState){
                            is DeviceUiState.Loading -> TopAppBarLoading()
                            is DeviceUiState.Loaded -> TopAppBarLoaded()
                            is DeviceUiState.Error -> {}
                        }
                    },
                    floatingActionButtonPosition = FabPosition.End,
                    floatingActionButton = {
                        when(state.uiState){
                            is DeviceUiState.Loading -> FloatingLoading()
                            is DeviceUiState.Loaded -> FloatingLoaded(deviceViewModel)
                            is DeviceUiState.Error -> {}
                        }
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        HomeScreen(
                            state = state,
                            retryAction = { deviceViewModel.send(GetDevicesEvent()) },
                            viewModel = deviceViewModel
                        )
                    }
                }
            }
        }
    }
}
