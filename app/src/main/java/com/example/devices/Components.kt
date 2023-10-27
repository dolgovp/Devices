package com.example.devices

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.devices.ui.theme.GrayCancel
import com.example.devices.ui.theme.PurpleText
import com.example.devices.ui.theme.RedDelete
import com.example.devices.ui.theme.StatusBoxBlue
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun Status(status: Boolean){
    var color = Color.Red
    var text = "OFF LINE"
    if (status){
        text = "ON LINE"
        color = Color.Green
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Box (
            contentAlignment= Alignment.Center,
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(color)

        ){}
        Text(text, color = Color.White)
    }
}
@Preview
@Composable
fun LoadingCard(){
    val paddingModifier = Modifier.padding(10.dp)
    val shape = RoundedCornerShape(20.dp)
    val shapeSmall = RoundedCornerShape(5.dp)
    // Сделать кастомный modifier
    Card(
        elevation = cardElevation(10.dp),
        modifier = paddingModifier
            .fillMaxWidth()
            .height(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Column(
            modifier = paddingModifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween) {
            Column(){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Box (
                        contentAlignment= Alignment.Center,
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.background)
                            .alpha(0.5F)

                    ){}
                    Box(
                        Modifier
                            .size(width = 46.dp, height = 12.dp)
                            .clip(shape)
                            .background(MaterialTheme.colorScheme.background)
                            .alpha(0.5F)
                    )
                }

                Spacer(Modifier.size(8.dp))
                Box(
                    Modifier
                        .size(width = 168.dp, height = 28.dp)
                        .clip(shapeSmall)
                        .background(MaterialTheme.colorScheme.background)
                        .alpha(0.5F)
                )
            }
            Box(
                Modifier
                    .size(width = 130.dp, height = 28.dp)
                    .clip(shape)
                    .background(MaterialTheme.colorScheme.background)
                    .alpha(0.5F)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarLoaded(){
    TopAppBar(
        title = { Text(
            stringResource(R.string.app_name),
            style = TextStyle(shadow = Shadow(
                color = Color.Black,
                offset = Offset.Zero,
                blurRadius = 8f
            )
            ),
            fontSize = 24.sp
        )
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = PurpleText
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarLoading(){
    TopAppBar(
        title = { Text(
            stringResource(R.string.app_name),
            fontSize = 24.sp)
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}

@Composable
fun WorkStatus(type: Int, status: String){
    val shape = RoundedCornerShape(20.dp)
    var text = "Газ не обнаружен"
    var id = R.drawable.paste
    if (type == 2){
       if (status == "2"){
            text = "Обнаружен газ"
       }else{
           id = R.drawable.paste1
       }
    } else {
        if (status=="Выключен"){
            id = R.drawable.paste1
        }
        text = status
    }
    Box(
        Modifier
            .height(28.dp)
            .clip(shape)
            .background(StatusBoxBlue)
    ){Row(
        modifier = Modifier
            .height(24.dp)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(id = id),
            contentDescription = null,
        )

        Text(text.uppercase(), color = Color.White)
    }

    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun Timer(time: Int){
    Box(
        Modifier
            .height(28.dp)
    ){Row(
        modifier = Modifier
            .height(24.dp)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(id = R.drawable.paste),
            contentDescription = null,
        )
        val format = SimpleDateFormat("HH:mm:ss")
        Text(format.format(Date(time.toLong())), color = Color.White)
    }

    }
}

@Composable
fun FloatingLoaded(deviceViewModel: DeviceViewModel){
    FloatingActionButton(
        onClick = {deviceViewModel.getDevices()},
        shape = RoundedCornerShape(50.dp),
        containerColor = Color.White
    ) {
        Text(text = "ОБНОВИТЬ", modifier = Modifier
            .padding(12.dp)
            .alpha(0.8F), color = Color.Black, fontSize = 18.sp, letterSpacing = 2.sp)
    }
}

@Composable
fun FloatingLoading(){
    FloatingActionButton(
        onClick = {},
        shape = RoundedCornerShape(50.dp),
        containerColor = Color.White
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(R.drawable.img),
            contentDescription = null
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogText: String,
) {
    AlertDialog(
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = RedDelete,
                    contentColor = Color.White
                )
            ) {
                Text("УДАЛИТЬ")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = GrayCancel,
                    contentColor = Color.White
                )
            ) {
                Text("ОТМЕНА")
            }
        }
    )
}