package com.example.devices

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.devices.model.Device
import com.example.devices.ui.theme.ButtonBlue
import com.example.devices.ui.theme.Grad1
import com.example.devices.ui.theme.Grad2
import com.example.devices.ui.theme.TextBlack
import java.util.Locale

@Composable
fun HomeScreen(
    state: MainState,
    retryAction: () -> Unit,
    viewModel: DeviceViewModel
){
    when(state.uiState){
        is DeviceUiState.Loading -> LoadingScreen()
        is DeviceUiState.Error -> ErrorScreen(retryAction, state.uiState.error)
        is DeviceUiState.Loaded -> DevicesScreen(state.devices, viewModel)
    }
}

@Composable
fun LoadingScreen(){
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(5){
            LoadingCard()
        }
    }
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    error: Exception
    ){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Что-то пошло не так,\n $error", textAlign = TextAlign.Center)
        Spacer(Modifier.size(20.dp))
        Button(onClick = retryAction, colors = ButtonDefaults.buttonColors(
            containerColor = ButtonBlue,
            contentColor = Color.White
        )
        ) {
            Text("ПОВТОРИТЬ")
        }
    }
}

@Composable
fun DevicesScreen(
    devices: List<Device>,
    viewModel: DeviceViewModel
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(12.dp)
    ) {
        items(items = devices, key = {device -> device.id}){ device ->
            DeviceCard(device, viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceCard(
    device: Device,
    deviceViewModel: DeviceViewModel
){
    val paddingModifier = Modifier.padding(10.dp)
    val openAlertDialog = remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(20.dp)
    val shapeSmall = RoundedCornerShape(5.dp)
    val gradient = Brush.verticalGradient(colors = listOf(Grad1, Grad2))
    // Сделать кастомный modifier
    if (openAlertDialog.value){
        AlertDialogExample(
            onDismissRequest = { openAlertDialog.value = false},
            onConfirmation = { deviceViewModel.send(DeleteEvent(device.id)) },
            dialogText = "Вы хотите удалить ${device.name.lowercase()}?"
        )
    }
    Card(
        elevation = CardDefaults.cardElevation(10.dp),
        modifier = paddingModifier
            .fillMaxWidth(),
        onClick = {
            openAlertDialog.value = true
        }
    ) {
        Box(
            Modifier
                .height(200.dp)
                .fillMaxWidth()
                .background(brush = gradient),
        ){
            Column(
                modifier = paddingModifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween) {
                Column(){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ){
                        Status(device.isOnline)
                    }
                    Spacer(Modifier.size(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Box(
                            Modifier
                                .width(160.dp)
                                .clip(shapeSmall)
                        ){
                            Text(device.name.uppercase(), fontSize = 24.sp, color = TextBlack)
                        }
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ){
                    WorkStatus(type = device.type, status = device.status)
                    if (device.isOnline){
                        Timer(time = device.lastWorkTime)
                    }
                }

            }
        }
    }
}