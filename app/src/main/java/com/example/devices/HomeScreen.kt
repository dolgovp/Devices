package com.example.devices

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.devices.model.Device

@Composable
fun HomeScreen(
    uiState: DeviceUiState,
    retryAction: () -> Unit
){
    when(uiState){
        is DeviceUiState.Loading -> LoadingScreen()
        is DeviceUiState.Error -> ErrorScreen(retryAction)
        is DeviceUiState.Loaded -> DevicesScreen(uiState.devices.data)
    }
}

@Composable
fun LoadingScreen(){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = null
        )
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Что-то пошло не так \n Ошибка 123")
        Button(onClick = retryAction) {
            Text("Повторить")
        }
    }
}

@Composable
fun DevicesScreen(
    devices: List<Device>
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(items = devices, key = {device -> device.id}){ device ->
            DeviceCard(device)
        }
    }
}

@Composable
fun DeviceCard(
    device: Device
){
    Column {
        Text(device.name)
        Log.d("log", "https://api.fasthome.io"+device.icon)
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .decoderFactory(SvgDecoder.Factory())
                .data("https://api.fasthome.io" + device.icon)
                .size(Size.ORIGINAL) // Set the target size to load the image at.
                .build()
        )
        Image(
            painter = painter,
            contentDescription = null
        )
    }


}